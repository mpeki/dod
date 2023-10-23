import { CharacterSkill } from "../../types/skill";
import { CharacterSkillItem } from "./CharacterSkillItem";
import { Table, TableBody, TableCell, TableHead, TableRow } from "@mui/material";
import { StyledTable } from "../../components/shared/Table.styled";
import { useTranslation } from "react-i18next";
import { height } from "@mui/system";

interface IProps {
  charId: string;
  skills: Record<string, CharacterSkill>;
  fetchCharHandler: () => void;
  canRemoveSkill: boolean;
}

export const CharacterSkillList = ({ charId, skills, fetchCharHandler, canRemoveSkill }: IProps): JSX.Element => {

  const { t } = useTranslation("char");
  const charSkillItems = () => {
    const result: JSX.Element[] = [];
    if (skills) {
      const skillMap: Map<string, CharacterSkill> = new Map(Object.entries(skills));
      skillMap.forEach((value, key) => {
        if (value != null) {
          let charSkill = skillMap.get(key);
          if (charSkill != null) {
            result.push(<CharacterSkillItem key={charSkill.skill.key} characterId={charId} charSkill={charSkill} fetchCharHandler={fetchCharHandler} canRemoveSkill={canRemoveSkill}/>);
          }
        }
      });
    }
    let emptyRows = 3 - result.length < 1 ? 0 : 3 - result.length
    for(let i = 0; i < emptyRows; i++) {
      result.push(<TableRow key={i} style={{ height: "25px" }}>
        <TableCell></TableCell>
        <TableCell></TableCell>
        <TableCell></TableCell>
        <TableCell></TableCell>
      </TableRow>);
    }

    return result;
  };


  return (
    <StyledTable>
      <Table>
        <TableHead>
          <TableRow>
            <TableCell sx={{ fontWeight: 'bold' }}>{t("detail.skills.skills")}</TableCell>
            <TableCell sx={{ fontWeight: 'bold' }}>{t("detail.skills.value")}</TableCell>
            <TableCell sx={{ fontWeight: 'bold' }}>{t("detail.skills.exp")}</TableCell>
            <TableCell width={1}/>
          </TableRow>
        </TableHead>
        <TableBody>
          {charSkillItems()}
        </TableBody>
      </Table>
    </StyledTable>
  );
};