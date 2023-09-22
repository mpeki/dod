import { Controller, useForm } from "react-hook-form";
import { useCallback, useEffect, useState } from "react";
import { Change, createChange } from "../../types/change";
import { useChangeService } from "../../services/change.service";
import classes from "../Character/AddCharacter.module.css";
import { SkillSelector } from "../../components/SkillSelector";
import { Skill } from "../../types/skill";
import { SkillUtil } from "../../services/skill.service";
import { Character } from "../../types/character";
import { Item } from "../../types/item";
import { ItemSelector } from "../Items/ItemSelector";
import { showInfoSnackbar, showWarningSnackbar } from "../../utils/DODSnackbars";
import { SecondaryChangeKey } from "../../types/secondary-change-key";

interface IProps {
  character: Character;
  buySkillHandler: any;
  onConfirm: any;
}

interface FormData {
  changeType: string;
  changeDescription: string;
  changeKey: string;
  modifier: number;
}

export const BuySkillForm = ({ character, buySkillHandler, onConfirm }: IProps) => {

  const { doChange } = useChangeService();

  const { getValues, register, formState: { errors }, handleSubmit, reset, control } = useForm<FormData>();
  const [selected, setSelected] = useState<Skill>();
  const [bspLeft, setBspLeft] = useState<number>(character.baseSkillPoints || 0);
  const [bspCost, setBspCost] = useState<number>(0);
  const [changeData, setChangeData] = useState<Change>(createChange("NEW_SKILL", "Buy new skill", "", -1));
  const [weapons, setWeapons] = useState<Item[]>([]);
  const [weaponSelected, setWeaponSelected] = useState("");

  const submitHandler = useCallback(async () => {
    if (selected) {
      let secondaryChange: SecondaryChangeKey | undefined = weaponSelected ? { changeType: "SKILL_FOR_ITEM_USE", changeKey: weaponSelected } : undefined;
      const changePostData: Change = createChange("NEW_SKILL", "Buy new skill", selected.key, secondaryChange, getValues("modifier"));

      if (character.id != null) {
        await doChange(character.id, changePostData).then((change: Change) => {
          showInfoSnackbar("Skill bought successfully")
        })
        .catch((e) => showWarningSnackbar((e as Error).message))
        .finally(() => {});
      }
      setChangeData(createChange());
      buySkillHandler();
      reset();
      onConfirm();
    }
  }, [selected, weaponSelected, changeData, getValues, character.id, buySkillHandler, reset, onConfirm, doChange]);

  const onSubmit = handleSubmit(submitHandler);

  const selectSkillHandler = (skill: Skill) => {
    setSelected(skill);
  };

  const handleModifierChange = (event: any) => {
    // 👇 Get input value from "event"
    let cost = SkillUtil.calculateNewSkillPrice(character, selected, event.target.value);
    setBspCost(cost);
    if (character.baseSkillPoints != null) {
      setBspLeft(character.baseSkillPoints - cost);
    }
  };

  const handleItemChange = (items: Item[], newInputValue: string) => {
    setWeaponSelected(newInputValue);
  };

  const fetchWeaponsHandler = useCallback(async () => {
    let itemJSON = localStorage.getItem("items");
    setWeapons(itemJSON === null ? null : JSON.parse(itemJSON).filter((item: Item) => item.itemType === "MELEE_WEAPON"));
  }, []);

  useEffect(() => {
    fetchWeaponsHandler().then();
  }, [fetchWeaponsHandler]);

  //create form that buys a skill and adds it to the character
  return (
    <form onSubmit={onSubmit}>
      <div>
        <label htmlFor="changeKey">Select skill:</label>
        <Controller
          name="changeKey"
          control={control}
          render={() => (
            <SkillSelector charSkills={character?.skills} selectSkillHandler={selectSkillHandler} />
          )}
        />
        {/*<input {...register("changeKey", { required: true })} />*/}
        {errors.changeKey?.type === "required" && <div className="error">You must name a skill</div>}
      </div>
      <div>
        {(selected?.key === "primary.weapon" || selected?.key === "secondary.weapon") && (
          <ItemSelector label={`Select ${selected.key}`} items={weapons} onChange={handleItemChange} />
        )}
      </div>
      <div>
        <div>BSPs Left: {bspLeft}</div>
        <div>BSPs Cost: {bspCost}</div>
        <label htmlFor="modifier">FV to buy:</label>
        <input {...register("modifier", { valueAsNumber: true, required: true, min: 1, max: 15 })}
               onChange={handleModifierChange} />
        {errors.modifier?.type === "required" && <div className="error">Must be a number between 1 and 15</div>}

      </div>
      <footer className={classes.actions}>
        <button type="submit">Buy</button>
        <button onClick={onConfirm}>Cancel</button>
      </footer>
    </form>
  );
};