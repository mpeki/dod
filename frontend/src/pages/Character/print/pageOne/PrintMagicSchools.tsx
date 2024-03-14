import { Character } from "../../../../types/character";
import { StyledTable } from "../../../../components/shared/Table.styled";
import { Table, TableBody, TableCell, TableHead, TableRow } from "@mui/material";
import { useTranslation } from "react-i18next";

interface IProps {
  character: Character;
}
export const PrintMagicSchools = ({character} : IProps) => {

  const { t } = useTranslation("char");
  const magicSchoolRows = () => {
    const result: JSX.Element[] = [];
    let emptyRows = 5
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
    <StyledTable style={{ marginTop: '15px' }}>
      <Table>
        <TableHead>
          <TableRow style={{ height: "22px" }}>
            <TableCell sx={{ width: 250, fontWeight: 'bold', borderColor: "darkgray" }}>{t("detail.skills.magicSchools")}</TableCell>
            <TableCell sx={{ width: 50, fontWeight: 'bold', borderColor: "darkgray" }}>{t("detail.skills.value")}</TableCell>
            <TableCell sx={{ fontWeight: 'bold', borderColor: "darkgray" }}>{t("detail.skills.exp")}</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {magicSchoolRows()}
        </TableBody>
      </Table>
    </StyledTable>
  );
}