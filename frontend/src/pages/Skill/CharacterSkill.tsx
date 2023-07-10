import { Skill } from "../../types/skill";
import { SkillDetails } from "./SkillDetails";
import { useEffect, useState } from "react";
import { TableCell, TableRow } from "@mui/material";
import { StyledTableRow } from "../../components/shared/Table.styled";
import { Category } from "../../types/category";

interface IProps {
  characterId: string;
  skill: Skill;
}

export const CharacterSkill = ({ characterId, skill }: IProps): JSX.Element => {

  const [showSkillDetails, setShowSkillDetails] = useState<boolean>();
  const [memSkill, setMemSkill] = useState<Skill>();

  const showSkillDetailsHandler = () => {
    if (showSkillDetails) {
      setShowSkillDetails(false);
    } else {
      setShowSkillDetails(true);
    }
  };

  useEffect(() => {
    let skillJSON = localStorage.getItem("skills");
    if (skillJSON !== null) {
      const skills: Skill[] = JSON.parse(skillJSON);
      if (skills !== undefined) {
        const memSkill: Skill = skills.filter(storedSkill => {
          return storedSkill.key === skill.key;
        })[0];
        memSkill.fv = skill.fv;
        memSkill.experience = skill.experience;
        memSkill.itemKey = skill.itemKey;
        setMemSkill(memSkill);
      }
    }
  }, [skill]);

  if (memSkill === undefined) {
    return <TableRow><TableCell>skill!</TableCell></TableRow>;
  }
  let skillCategory = JSON.stringify(memSkill.category).replace(/"/g, '')  as string;

  return (
    <>
      {showSkillDetails && (
        <SkillDetails characterId={characterId} skill={memSkill} onConfirm={showSkillDetailsHandler} />
      )}
      <TableRow hover key={memSkill.key} onClick={showSkillDetailsHandler} >
        <TableCell>{memSkill.itemKey ? `${memSkill.itemKey} (${memSkill.key})` : memSkill.key}</TableCell>
        <TableCell>{skillCategory === "B" ? 'B'+memSkill.fv : memSkill.fv}</TableCell>
        <TableCell>{memSkill.experience}</TableCell>
      </TableRow>
    </>
  );

};