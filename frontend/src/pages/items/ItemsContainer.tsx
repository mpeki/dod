import { Character } from "../../types/character";
import { Table, TableBody, TableCell, TableHead, TableRow } from "@mui/material";
import { StyledTable } from "../../components/shared/Table.styled";
import { BodyPartItem } from "../Character/BodyPart";

interface IProps {
  // character: Character;
}


export const ItemsContainer = ({ }: IProps) => {

  const itemRows  = function(): any[] {

    const items = []
    for (let i = 0; i < 15; i++) {
      items.push(<TableRow>
          <TableCell height={12}> </TableCell>
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
            <TableCell>Item</TableCell>
            <TableCell></TableCell>
            <TableCell></TableCell>
            <TableCell></TableCell>
            <TableCell></TableCell>
            <TableCell></TableCell>
            <TableCell></TableCell>
            <TableCell>Weight</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {itemRows()}
        </TableBody>
      </Table>
    </StyledTable>

  );
};