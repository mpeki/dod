import { Controller, useForm } from "react-hook-form";
import { useCallback, useState } from "react";
import { Change } from "../../types/change";
import { ChangeService } from "../../services/change.service";
import classes from "../Character/AddCharacter.module.css";
import { SkillSelector } from "../../components/SkillSelector";
import { Skill } from "../../types/skill";

interface IProps {
  charId: string;
  buySkillHandler: any;
  onConfirm: any;
}

interface FormData {
  changeType: string;
  changeDescription: string;
  changeKey: string;
  modifier: number;
}

export const BuySkillForm = ({ charId, buySkillHandler, onConfirm }: IProps) => {

  const { getValues, register, formState: { errors }, handleSubmit, reset, control } = useForm<FormData>();
  const [selected, setSelected] = useState<Skill | null>(null);
  const [changeData, setChangeData] = useState<Change>({
    changeKey: "",
    changeType: "NEW_SKILL",
    changeDescription: "Buy new skill",
    modifier: 0
  });

  const submitHandler = useCallback(async () => {
    console.log("selected: " + JSON.stringify(selected));
    if (selected) {
      const changePostData: Change = {
        ...changeData,
        changeKey: selected.key,
        modifier: getValues('modifier'),
      };
      console.log("changePostData: " + JSON.stringify(changePostData));
      await ChangeService.buySkill(charId, changePostData);
      setChangeData({ changeKey: "", modifier: 0, changeType: "NEW_SKILL", changeDescription: "Buy new skill"});
      buySkillHandler();
      reset();
      onConfirm();
    }
  }, [buySkillHandler, changeData, getValues, onConfirm, reset, selected]);

  const onSubmit = handleSubmit(submitHandler);

  const selectSkillHandler = (skill: Skill) => {
    console.log("Skill: " + JSON.stringify(skill));
    setSelected(skill);
  }

  //create form that buys a skill and adds it to the character



  return (
    <form onSubmit={onSubmit}>
      <div>
        <label htmlFor="changeKey">Select skill:</label>
        <Controller
          name="changeKey"
          control={control}
          render={({ field }) => (
            <SkillSelector selectSkillHandler={selectSkillHandler} />
            )}
          />
        {/*<input {...register("changeKey", { required: true })} />*/}
        {errors.changeKey?.type === 'required' && <div className="error">You must name a skill</div>}
      </div>
      <div>
        <label htmlFor="modifier">FV to buy:</label>
        <input {...register("modifier", { valueAsNumber: true, required: true, min: 1, max: 15 } )} />
        {errors.modifier?.type === 'required' && <div className="error">Must be a number between 1 and 15</div>}
      </div>
      <footer className={classes.actions}>
        <button type='submit'>Create</button>
        <button onClick={onConfirm}>Cancel</button>
      </footer>
    </form>

  )
}