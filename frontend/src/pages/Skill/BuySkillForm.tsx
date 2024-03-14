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
import { showSuccessSnackbar, showWarningSnackbar } from "../../utils/DODSnackbars";
import { SecondaryChangeKey } from "../../types/secondary-change-key";
import { GroupType } from "../../types/group";
import { Category } from "../../types/category";
import CharacterContext from "../Character/CharacterContext";
import { useTranslation } from "react-i18next";
import { Box, Button, Divider, Grid, List, ListItem, TextField } from "@mui/material";
import { CharacterBasePoints } from "../Character/CharacterBasePoints";

interface IProps {
  character: Character;
  onConfirm: any;
}

interface FormData {
  changeType: string;
  changeDescription: string;
  changeKey: string;
  modifier?: number;
}

export const BuySkillForm = ({ character, onConfirm }: IProps) => {

  const { doChange } = useChangeService();

  const {
    setError, clearErrors, watch, getValues, register,
    formState: { errors, isSubmitting }, handleSubmit, reset,
    control, setValue
  } = useForm<FormData>({ defaultValues: { modifier: undefined } });
  const watchModifier = watch("modifier");
  const [selected, setSelected] = useState<Skill>();
  const [bspLeft, setBspLeft] = useState<number>(character.baseSkillPoints || 0);
  const [bspCost, setBspCost] = useState<number>(0);
  const [changeData, setChangeData] = useState<Change>(createChange("NEW_SKILL", "Buy new skill", "", undefined));
  const [weapons, setWeapons] = useState<Item[]>([]);
  const [weaponSelected, setWeaponSelected] = useState("");
  const charContext = useContext(CharacterContext);

  if (!charContext) {
    throw new Error("SkillContainer must be rendered within an ActivateCharContext.Provider");
  }

  const { fetchCharHandler, currentCharacter } = charContext;
  const { t } = useTranslation(["skills", "validation"]);


  const submitHandler = useCallback(async () => {
    if (selected) {
      let secondaryChange: SecondaryChangeKey | undefined = weaponSelected ? {
        changeType: "SKILL_FOR_ITEM_USE",
        changeKey: weaponSelected
      } : undefined;
      const changePostData: Change = createChange("NEW_SKILL", "Buy new skill", selected.key, secondaryChange, getValues("modifier"));

      if (currentCharacter != null && currentCharacter.id != null) {
        await doChange(currentCharacter.id, changePostData).then((change: Change) => {
          showSuccessSnackbar(t("buySkillsForm.buySkillSuccess", { skill: t(selected.key) }));
        })
        .catch((e) => showWarningSnackbar((e as Error).message))
        .finally(() => {
          setChangeData(createChange());
          if (currentCharacter.id != null) {
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
    if (skill) {
      setSelected(skill);
    } else {
      setSelected(undefined);
    }
  };

  const handleModifierChange = (event: any) => {
    // 👇 Get input value from "event"

    let cost = 0
    try {
      cost = SkillUtil.calculateNewSkillPrice(character, selected, event.target.value);
    } catch (e) {
      if (e instanceof Error) {
        setError("modifier", {
          type: "custom",
          message: t(`validation:${e.message}`)
        });
      } else {
        // Handle other types of unexpected errors
        // e.g., log them or set a generic error message
        console.error("An unexpected error occurred:", e);
      }
      return;
    }
    clearErrors("modifier");
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
  const languageSkills: Record<string, Skill> = {
    [`speak.${character.race.motherTongue}`]: createSkill(`speak.${character.race.motherTongue}`, langGroup, langCategory),
    [`rw.${character.race.motherTongue}`]: createSkill(`speak.${character.race.motherTongue}`, langGroup, langCategory)
  };
  const excludedSkills = { ...characterSkillsToSkills(character.skills || {}), ...languageSkills };

  //create form that buys a skill and adds it to the character
  return (
    <form onSubmit={onSubmit}>
      <List dense={false}>
        <ListItem>
          <Controller name="changeKey" control={control}
                      render={() => (
                        <SkillSelector excludedSkills={excludedSkills} selectSkillHandler={selectSkillHandler}
                                       raceName={character.race.name} />
                      )}
          />
        </ListItem>
        {(selected && (selected?.key === "primary.weapon" || selected?.key === "secondary.weapon")) && (
          <ListItem><Box ml={1}><ItemSelector label={t("buySkillsForm.itemPlaceholder", { item: t(selected.key) })}
                                              items={weapons}
                                              onChange={handleItemChange} /></Box></ListItem>
        )}
        <ListItem>
          <Box ml={1}>
            <TextField required type="number" disabled={selected === undefined} variant="standard"
                       placeholder={t("buySkillsForm.fvInputField")}
                       {...register("modifier", {
                         valueAsNumber: true,
                         required: t("validation:required"),
                         min: { value: 1, message: t("validation:minValue", { min: 1 }) },
                         max: { value: 15, message: t("validation:maxValue", { max: 15 }) }
                       })} error={Boolean(errors.modifier)} helperText={errors.modifier?.message}
                       onChange={(e) => {
                         handleModifierChange(e);
                         // Ensure react-hook-form's register onChange is called
                         register("modifier").onChange(e);
                       }} />
            {errors.modifier?.type === "required" &&
              <div className="error">{t("validation:valueBetween", { min: 1, max: 15 })}</div>}
          </Box>
        </ListItem>
      </List>
      <Box pt={4}>
        <Divider textAlign="left">{t("buySkillsForm.BPDivider")}</Divider>
        <Box ml={2} pt={2}>
          <CharacterBasePoints character={character} expected={bspCost} />
        </Box>
      </Box>
      <footer className={classes.actions}>
        <Grid container spacing={1} pt={2}>
          <Grid item>
            <Button disabled={Object.keys(errors).length > 0 || isSubmitting || !watchModifier || bspLeft < 0} variant="contained" color="success"
                    type="submit">{t("buySkillsForm.buyButton")}</Button>
          </Grid>
          <Grid item>
            <Button color="secondary" onClick={onConfirm}>{t("buySkillsForm.cancelButton")}</Button>
          </Grid>
        </Grid>
      </footer>
    </form>
  );
};