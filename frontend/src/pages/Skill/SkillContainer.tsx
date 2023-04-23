import { Skill } from "../../types/skill";
import { useState } from "react";
import { BuySkill } from "./BuySkill";
import { CharacterSkillList } from "./CharacterSkillList";

interface IProps {
  charId: string;
  baseSkillPoints: number | undefined
  skills: Map<string, Skill> | undefined;
  fetchCharHandler: () => void;
}

export const SkillContainer = ({ charId, baseSkillPoints, skills, fetchCharHandler }: IProps): JSX.Element => {

  const [showBuySkill, setShowBuySkill] = useState<boolean>()

  const showBuySkillHandler = () => {
    if(showBuySkill){
      setShowBuySkill(false);
    } else {
      setShowBuySkill(true)
    }
  }


  return (
    <>
      <h2>Skills</h2>
      <p>Base Skill Points: {baseSkillPoints}</p>
      <button onClick={showBuySkillHandler}>New Skill</button>
      {showBuySkill && <BuySkill charId={charId} onConfirm={showBuySkillHandler} buySkillHandler={fetchCharHandler}/>}
      <CharacterSkillList skills={skills} />
    </>
  );

};