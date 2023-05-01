import { Skill } from "../../types/skill";
import classes from "./SkillDetails.module.css";
import { useCallback, useEffect, useState } from "react";
import { CharacterService } from "../../services/character.service";
import { BuySkillForm } from "./BuySkillForm";
import { SkillService } from "../../services/skill.service";
interface IProps {
  characterId: string;
  skill: Skill;
  onConfirm: any;
}
export const SkillDetails = ({characterId, skill, onConfirm} : IProps): JSX.Element => {

  const [memSkill, setMemSkill] = useState<Skill>()

  useEffect(() => {
    let skillJSON = localStorage.getItem("skills");
    if (skillJSON !== null) {
      const skills: Skill[] = JSON.parse(skillJSON);
      if(skills !== undefined){
        const memSkill: Skill = skills.filter(storedSkill => {
          return storedSkill.key === skill.key;
        })[0];
        setMemSkill(memSkill);
      }
    }
  }, [skill]);

  const trainSkillHandler = useCallback(async () => {
    console.log("trainSkillHandler");
    SkillService.trainSkill(characterId, skill.key)
    .then((character) => {
      // skill =
    })
    .catch((e) => alert("Error fetching character: " + e));
  }, []);

  return (
    <>
      <div>
        <div className={classes.backdrop} onClick={onConfirm}></div>
        <div className={classes.modal}>
          <header className={classes.header}>
            <h2>Skill Details</h2>
          </header>
          <div className={classes.content}>
            <p>Name: {skill.key}</p>
            <p>FV: {skill.fv}</p>
            <p>Skill Points: {skill.experience}</p>
            <p>Base Chance: {memSkill?.baseChance} group</p>
            <p>Category: {memSkill?.category.toString()}</p>
            <p>Group: {memSkill?.group.toString()}</p>
            <p>Trait: {memSkill?.traitName}</p>
            <p>Price: {memSkill?.price}</p>
          </div>
          <footer className={classes.actions}>
            <button onClick={trainSkillHandler}>Train Skill</button>
            <button onClick={onConfirm}>Cancel</button>
          </footer>
        </div>
      </div>
    </>
  )
}
