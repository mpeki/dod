import { Skill } from "../../types/skill";
import { SkillDetails } from "./SkillDetails";
import { useState } from "react";
import { TableCell, TableRow } from "@mui/material";
import { StyledTableRow } from "../../components/shared/Table.styled";

interface IProps {
  characterId: string;
  skill: Skill;
}

export const CharacterSkill = ({ characterId, skill }: IProps): JSX.Element => {

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
        <SkillDetails characterId={characterId} skill={skill} onConfirm={showSkillDetailsHandler} />
      )}
      <TableRow hover key={skill.key} onClick={showSkillDetailsHandler} >
        <TableCell>{skill.key}</TableCell>
        <TableCell>{skill.fv}</TableCell>
        <TableCell>{skill.experience}</TableCell>
      </TableRow>
    </>
  );

};