import { Character } from "../../../../types/character";
import { useTranslation } from "react-i18next";
import { Table, TableBody, TableCell, TableHead, TableRow } from "@mui/material";
import { StyledTable } from "../../../../components/shared/Table.styled";

interface IProps {
  character: Character;
}

export const PrintSpells = ({character} : IProps) => {

  const { t } = useTranslation("char");
  const spellRows = () => {
    const result: JSX.Element[] = [];
    let emptyRows = 14
    for(let i = 0; i < emptyRows; i++) {
      result.push(<TableRow key={i} style={{ height: "22px" }}>
        <TableCell sx={{ borderRight: 1, borderColor: "darkgray" }}></TableCell>
        <TableCell sx={{ borderRight: 1, borderColor: "darkgray" }}></TableCell>
        <TableCell sx={{ borderRight: 0, borderColor: "darkgray" }}></TableCell>
      </TableRow>);
    }

    return result;
  };


  return (
    <StyledTable>
      <Table>
        <TableHead>
          <TableRow style={{ height: "22px" }}>
            <TableCell sx={{ width: 250, fontWeight: 'bold',borderColor: "darkgray" }}>{t("detail.skills.spells")}</TableCell>
            <TableCell sx={{ width: 50, fontWeight: 'bold', borderColor: "darkgray" }}>{t("detail.skills.value")}</TableCell>
            <TableCell sx={{ fontWeight: 'bold', borderColor: "darkgray" }}>{t("detail.skills.exp")}</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {spellRows()}
        </TableBody>
      </Table>
    </StyledTable>
  );
}