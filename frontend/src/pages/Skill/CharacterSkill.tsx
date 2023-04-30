import { Skill } from "../../types/skill";
import { SkillDetails } from "./SkillDetails";
import { useState } from "react";

interface IProps {
  characterId: string;
  skill: Skill;
}
export const CharacterSkill = ({characterId, skill} : IProps): JSX.Element => {

  const [showSkillDetails, setShowSkillDetails] = useState<boolean>();
  const showSkillDetailsHandler = () => {
    if (showSkillDetails) {
      setShowSkillDetails(false);
    } else {
      setShowSkillDetails(true);
    }
  };

  return (
    <>
      {showSkillDetails && (
        <SkillDetails characterId={characterId} skill={skill} onConfirm={showSkillDetailsHandler}/>
      )}
      <div key={skill.key} onClick={showSkillDetailsHandler}>{skill.key} - {skill.fv}</div>
    </>
  )

}