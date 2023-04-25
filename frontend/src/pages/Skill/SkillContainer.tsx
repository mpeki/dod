import { Skill } from "../../types/skill";
import { useState } from "react";
import { BuySkill } from "./BuySkill";
import { CharacterSkillList } from "./CharacterSkillList";
import { Character } from "../../types/character";

interface IProps {
  character: Character;
  skills: Record<string, Skill> | undefined;
  fetchCharHandler: () => void;
}

export const SkillContainer = ({ character, skills, fetchCharHandler }: IProps): JSX.Element => {

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
      <p>Base Skill Points: {character.baseSkillPoints}</p>
      <button onClick={showBuySkillHandler}>New Skill</button>
      {showBuySkill && <BuySkill character={character} onConfirm={showBuySkillHandler} buySkillHandler={fetchCharHandler}/>}
      <CharacterSkillList skills={skills} />
    </>
  );

};