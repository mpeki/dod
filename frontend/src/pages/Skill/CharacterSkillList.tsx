import { Skill } from "../../types/skill";
import { CharacterSkill } from "./CharacterSkill";
import { Table, TableBody, TableCell, TableHead, TableRow } from "@mui/material";
import { StyledTable } from "../../components/shared/Table.styled";
import { useTranslation } from "react-i18next";

interface IProps {
  charId: string;
  skills: Record<string, Skill>;
  fetchCharHandler: () => void;
  canRemoveSkill: boolean;
}

export const CharacterSkillList = ({ charId, skills, fetchCharHandler, canRemoveSkill }: IProps): JSX.Element => {

  const { t } = useTranslation("char");

  const charSkillItems = () => {
    const result: JSX.Element[] = [];
    if (skills) {
      const skillMap: Map<string, Skill> = new Map(Object.entries(skills));
      skillMap.forEach((value, key) => {
        if (value != null) {
          let skill = skillMap.get(key);
          if (skill != null) {
            result.push(<CharacterSkill key={skill.key} characterId={charId} skill={skill} fetchCharHandler={fetchCharHandler} canRemoveSkill={canRemoveSkill}/>);
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
            {canRemoveSkill && <TableCell width={1}/>}
            <TableCell sx={{ fontWeight: 'bold' }}>{t("detail.skills.skills")}</TableCell>
            <TableCell sx={{ fontWeight: 'bold' }}>{t("detail.skills.value")}</TableCell>
            <TableCell sx={{ fontWeight: 'bold' }}>{t("detail.skills.exp")}</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {charSkillItems()}
        </TableBody>
      </Table>
    </StyledTable>
  );
};