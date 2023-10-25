import { CharacterSkill } from "../../types/skill";
import { CharacterSkillItem } from "./CharacterSkillItem";
import { Table, TableBody, TableCell, TableHead, TableRow } from "@mui/material";
import { StyledTable } from "../../components/shared/Table.styled";
import { useTranslation } from "react-i18next";
import { useContext } from "react";
import CharacterContext from "../Character/CharacterContext";

interface IProps {
  charId: string;
  skills: Record<string, CharacterSkill>;
  canRemoveSkill: boolean;
  isPrinting: boolean;
}

export const CharacterSkillList = ({ charId, skills, canRemoveSkill, isPrinting }: IProps): JSX.Element => {

  const { t } = useTranslation("char");

  const charSkillItems = () => {
    const result: JSX.Element[] = [];
    if (skills) {
      const skillMap: Map<string, CharacterSkill> = new Map(Object.entries(skills));
      skillMap.forEach((value, key) => {
        if (value != null) {
          let charSkill = skillMap.get(key);
          if (charSkill != null) {
            result.push(<CharacterSkillItem key={charSkill.skill.key} charSkill={charSkill} canRemoveSkill={canRemoveSkill} isPrinting={isPrinting}/>);
          }
        }
      });
    }
    // Add empty rows to fill the table
    let extraRows = isPrinting ? 35 : 3;
    let emptyRows = extraRows - result.length < 1 ? 0 : extraRows - result.length
    for(let i = 0; i < emptyRows; i++) {
      result.push(<TableRow key={i} style={{ height: "28px" }}>
        <TableCell sx={{ borderRight: isPrinting ? 1 : 0, borderColor: "lightgray" }}></TableCell>
        <TableCell sx={{ borderRight: isPrinting ? 1 : 0, borderColor: "lightgray" }}></TableCell>
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