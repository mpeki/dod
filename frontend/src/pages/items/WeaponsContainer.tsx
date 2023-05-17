import { Character } from "../../types/character";
import { Table, TableBody, TableCell, TableHead, TableRow } from "@mui/material";
import { StyledTable } from "../../components/shared/Table.styled";
import { BodyPartItem } from "../Character/BodyPart";

interface IProps {
  // character: Character;
}


export const WeaponsContainer = ({ }: IProps) => {

  const itemRows  = function(): any[] {

    const items = []
    for (let i = 0; i < 10; i++) {
      items.push(<TableRow >
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
            <TableCell>Weapon/Shield</TableCell>
            <TableCell>Fv</TableCell>
            <TableCell>Dmg</TableCell>
            <TableCell>Vl</TableCell>
            <TableCell>Bv</TableCell>
            <TableCell>Abs</TableCell>
            <TableCell>Range</TableCell>
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