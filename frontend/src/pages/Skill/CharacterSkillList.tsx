import { Skill } from "../../types/skill";
import { CharacterSkill } from "./CharacterSkill";
import { Table, TableBody, TableCell, TableHead, TableRow } from "@mui/material";
import { StyledTable } from "../../components/shared/Table.styled";

interface IProps {
  charId: string;
  skills: Record<string, Skill>;
}

export const CharacterSkillList = ({ charId, skills }: IProps): JSX.Element => {

  const charSkillItems = () => {
    const result: JSX.Element[] = [];
    if (skills) {
      const skillMap: Map<string, Skill> = new Map(Object.entries(skills));
      skillMap.forEach((value, key) => {
        if (value != null) {
          let skill = skillMap.get(key);
          if (skill != null) {
            result.push(<CharacterSkill key={skill.key} characterId={charId} skill={skill} />);
          }
        }
      });
    }
    return result;
  };


  return (
    <StyledTable>
      <Table>
        <TableHead>
          <TableRow>
            <TableCell sx={{ fontWeight: 'bold' }}>Skills</TableCell>
            <TableCell sx={{ fontWeight: 'bold' }}>Value</TableCell>
            <TableCell sx={{ fontWeight: 'bold' }}>Exp</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {charSkillItems()}
        </TableBody>
      </Table>
    </StyledTable>
  );
};