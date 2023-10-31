import { Controller, useForm } from "react-hook-form";
import { useCallback, useContext, useEffect, useState } from "react";
import { Change, createChange } from "../../types/change";
import { useChangeService } from "../../services/change.service";
import classes from "../Character/AddCharacter.module.css";
import { SkillSelector } from "../../components/SkillSelector";
import { CharacterSkill, createSkill, Skill } from "../../types/skill";
import { SkillUtil } from "../../services/skill.service";
import { Character } from "../../types/character";
import { Item } from "../../types/item";
import { ItemSelector } from "../Items/ItemSelector";
import { showInfoSnackbar, showWarningSnackbar } from "../../utils/DODSnackbars";
import { SecondaryChangeKey } from "../../types/secondary-change-key";
import { GroupType } from "../../types/group";
import { Category } from "../../types/category";
import CharacterContext from "../Character/CharacterContext";

interface IProps {
  character: Character;
  onConfirm: any;
}

interface FormData {
  changeType: string;
  changeDescription: string;
  changeKey: string;
  modifier: number;
}

export const BuySkillForm = ({ character, onConfirm }: IProps) => {

  const { doChange } = useChangeService();

  const { getValues, register, formState: { errors }, handleSubmit, reset, control } = useForm<FormData>();
  const [selected, setSelected] = useState<Skill>();
  const [bspLeft, setBspLeft] = useState<number>(character.baseSkillPoints || 0);
  const [bspCost, setBspCost] = useState<number>(0);
  const [changeData, setChangeData] = useState<Change>(createChange("NEW_SKILL", "Buy new skill", "", -1));
  const [weapons, setWeapons] = useState<Item[]>([]);
  const [weaponSelected, setWeaponSelected] = useState("");
  const charContext = useContext(CharacterContext);

  if (!charContext) {
    throw new Error("SkillContainer must be rendered within an ActivateCharContext.Provider");
  }

  const { fetchCharHandler, currentCharacter } = charContext;


  const submitHandler = useCallback(async () => {
    if (selected) {
      let secondaryChange: SecondaryChangeKey | undefined = weaponSelected ? { changeType: "SKILL_FOR_ITEM_USE", changeKey: weaponSelected } : undefined;
      const changePostData: Change = createChange("NEW_SKILL", "Buy new skill", selected.key, secondaryChange, getValues("modifier"));

      if (currentCharacter != null && currentCharacter.id != null) {
        await doChange(currentCharacter.id, changePostData).then((change: Change) => {
          showInfoSnackbar("Skill bought successfully")
        })
        .catch((e) => showWarningSnackbar((e as Error).message))
        .finally(() => {
          setChangeData(createChange());
          if(currentCharacter.id != null){
            fetchCharHandler(currentCharacter.id);
          }
          reset();
          onConfirm();
        });
      }
    }
  }, [selected, weaponSelected, changeData, getValues, character.id, fetchCharHandler, reset, onConfirm, doChange]);

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

  function characterSkillsToSkills(characterSkills: Record<string, CharacterSkill>): Record<string, Skill> {
    const skills: Record<string, Skill> = {};

    for (let key in characterSkills) {
      skills[key] = characterSkills[key].skill;
    }

    return skills;
  }


  // const excludedSkills: Record<string,Skill> | undefined = character.skills;
  const langGroup = new GroupType(GroupType.values.LANGUAGES);
  const langCategory = new Category(Category.values.B);
  const languageSkills: Record<string,Skill> = {
    [`speak.${character.race.motherTongue}`] : createSkill(`speak.${character.race.motherTongue}`, langGroup, langCategory ),
    [`rw.${character.race.motherTongue}`] : createSkill(`speak.${character.race.motherTongue}`, langGroup, langCategory)
  };
  const excludedSkills = { ...characterSkillsToSkills(character.skills || {}), ...languageSkills };

  //create form that buys a skill and adds it to the character
  return (
    <form onSubmit={onSubmit}>
      <div>
        <label htmlFor="changeKey">Select skill:</label>
        <Controller
          name="changeKey"
          control={control}
          render={() => (
            <SkillSelector excludedSkills={excludedSkills} selectSkillHandler={selectSkillHandler} raceName={character.race.name}/>
          )}
        />
        {errors.changeKey?.type === "required" && <div className="error">You must name a skill</div>}
      </div>
        {(selected?.key === "primary.weapon" || selected?.key === "secondary.weapon") && (
          <div><ItemSelector label={`Select ${selected.key}`} items={weapons} onChange={handleItemChange} /></div>
        )}
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