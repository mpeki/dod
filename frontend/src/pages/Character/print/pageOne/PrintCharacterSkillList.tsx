import { CharacterSkill } from "../../../../types/skill";
import { CharacterSkillItem } from "../../../Skill/CharacterSkillItem";
import { Table, TableBody, TableCell, TableHead, TableRow } from "@mui/material";
import { StyledTable } from "../../../../components/shared/Table.styled";
import { useTranslation } from "react-i18next";
import { Character } from "../../../../types/character";
import { useContext } from "react";
import CharacterContext from "../../CharacterContext";

interface IProps {
  skills: Record<string, CharacterSkill>;
  canRemoveSkill: boolean;
  isPrinting: boolean;
}

export const PrintCharacterSkillList = ({ skills, canRemoveSkill, isPrinting }: IProps): JSX.Element => {

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
    let extraRows = 27;
    let emptyRows = extraRows - result.length < 1 ? 0 : extraRows - result.length
    for(let i = 0; i < emptyRows; i++) {
      result.push(<TableRow key={i} style={{ height: "26px" }}>
        <TableCell sx={{ borderRight: 1, borderColor: "darkgray" }}></TableCell>
        <TableCell sx={{ borderRight: 1, borderColor: "darkgray" }}></TableCell>
        <TableCell sx={{ borderColor: "darkgray" }}></TableCell>
      </TableRow>);
    }

    return result;
  };


  return (
    <StyledTable style={{ marginTop: '15px' }}>
      <Table>
        <TableHead>
          <TableRow>
            <TableCell sx={{ borderBottom: 1, borderColor: "darkgray", width: 200, fontWeight: 'bold' }}>{t("detail.skills.skills")}</TableCell>
            <TableCell sx={{ borderBottom: 1, borderColor: "darkgray", fontWeight: 'bold' }}>{t("detail.skills.value").charAt(0)}</TableCell>
            <TableCell sx={{ borderBottom: 1, borderColor: "darkgray", width: 130, fontWeight: 'bold' }}>{t("detail.skills.exp")}</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {charSkillItems()}
        </TableBody>
      </Table>
    </StyledTable>
  );
};