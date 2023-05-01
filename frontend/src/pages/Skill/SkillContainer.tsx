import { Skill } from "../../types/skill";
import { useState } from "react";
import { BuySkill } from "./BuySkill";
import { CharacterSkillList } from "./CharacterSkillList";
import { Character } from "../../types/character";
import { CharacterState } from "../../types/character-state";

interface IProps {
  character: Character;
  skills: Record<string, Skill> | undefined;
  fetchCharHandler: () => void;
}

export const SkillContainer = ({
  character,
  skills,
  fetchCharHandler,
}: IProps): JSX.Element => {

  const [showBuySkill, setShowBuySkill] = useState<boolean>();

  const showBuySkillHandler = () => {
    if (showBuySkill) {
      setShowBuySkill(false);
    } else {
      setShowBuySkill(true);
    }
  };

  let canBuySkill = character.state === CharacterState.INIT_COMPLETE;

  return (
    <>
      <h2>Skills</h2>
      <p>Base Skill Points: {character.baseSkillPoints}</p>
      <button onClick={showBuySkillHandler} disabled={!canBuySkill}>
        New Skill
      </button>
      {showBuySkill && (
        <BuySkill
          character={character}
          onConfirm={showBuySkillHandler}
          buySkillHandler={fetchCharHandler}
        />
      )}
      <CharacterSkillList charId={character.id || ""} skills={skills || {} } />
    </>
  );
};
