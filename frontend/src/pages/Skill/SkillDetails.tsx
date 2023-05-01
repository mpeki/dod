import { Skill } from "../../types/skill";
import { Action } from "../../types/action";
import classes from "./SkillDetails.module.css";
import { useCallback, useEffect, useState } from "react";
import { SkillService } from "../../services/skill.service";
import { Change } from "../../types/change";
import { ChangeService } from "../../services/change.service";

interface IProps {
  characterId: string;
  skill: Skill;
  onConfirm: any;
}

export const SkillDetails = ({ characterId, skill, onConfirm }: IProps): JSX.Element => {

  const [action , setAction] = useState<Action>();
  const [memSkill, setMemSkill] = useState<Skill>();
  const [skillInput, setSkillInput] = useState<Skill>(skill);

  useEffect(() => {
    let skillJSON = localStorage.getItem("skills");
    if (skillJSON !== null) {
      const skills: Skill[] = JSON.parse(skillJSON);
      if (skills !== undefined) {
        const memSkill: Skill = skills.filter(storedSkill => {
          return storedSkill.key === skill.key;
        })[0];
        setMemSkill(memSkill);
      }
    }
  }, [skill]);

  const trainSkillHandler = useCallback(async () => {
    await SkillService.trainSkill(characterId, skill.key)
    .then((action) => {
      if (action.actor.skills !== undefined) {
        setAction(action);
        setSkillInput(action.actor.skills[skill.key]);
      }

    })
    .catch((e) => alert("Error fetching character: " + e));
  }, [characterId, skill]);


  const exchangeXPHandler = useCallback(async () => {
    const changeData: Change = {
      changeType: "SKILL_CHANGE",
      changeDescription: "Increase FV for skill",
      changeKey: skill.key,
      modifier: 1,
    };
    if(characterId != null) {
      await ChangeService.doChange(characterId, changeData)
      .then((change: Change) => {
        if (change.objectAfterChange?.skills !== undefined) {
          setAction(action);
          setSkillInput(change.objectAfterChange.skills[skill.key]);
        }

      });
    }
  }, [characterId, skill]);

  return (
    <>
      <div>
        <div className={classes.backdrop} onClick={onConfirm}></div>
        <div className={classes.modal}>
          <header className={classes.header}>
            <h2>Skill Details</h2>
          </header>
          <div className={classes.content}>
            <div>{action?.actionResult}</div>
            <p>Name: {skillInput.key}</p>
            <p>FV: {skillInput.fv}</p>
            <p>Skill Points: {skillInput.experience}</p>
            <p>Base Chance: {memSkill?.baseChance} group</p>
            <p>Category: {memSkill?.category.toString()}</p>
            <p>Group: {memSkill?.group.toString()}</p>
            <p>Trait: {memSkill?.traitName}</p>
            <p>Price: {memSkill?.price}</p>
          </div>

          <footer className={classes.actions}>
            <button onClick={exchangeXPHandler}>Buy FV</button>
            <button onClick={trainSkillHandler}>Train Skill</button>
            <button onClick={onConfirm}>Ok</button>
          </footer>
        </div>
      </div>
    </>
  );
};
