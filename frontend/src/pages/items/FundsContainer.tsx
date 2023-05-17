import { Table, TableBody, TableCell, TableHead, TableRow } from "@mui/material";
import { StyledTable } from "../../components/shared/Table.styled";

interface IProps {
  // character: Character;
}


export const FundsContainer = ({ }: IProps) => {

  const itemRows  = function(): any[] {

    const items = []
    for (let i = 0; i < 15; i++) {
      items.push(<TableRow >
          <TableCell> </TableCell>
          <TableCell> </TableCell>
          <TableCell> </TableCell>
          <TableCell> </TableCell>
          <TableCell> </TableCell>
          <TableCell> </TableCell>
          <TableCell> </TableCell>
          <TableCell> </TableCell>
        </TableRow>
      )
    }
    return items;
  }


  return (
    <StyledTable>
      <Table>
        <TableHead>
          <TableRow>
            <TableCell>Gold</TableCell>
            <TableCell></TableCell>
            <TableCell>Silver</TableCell>
            <TableCell></TableCell>
            <TableCell>Copper</TableCell>
            <TableCell></TableCell>
          </TableRow>
        </TableHead>
      </Table>
    </StyledTable>

  );
};