import { Table, TableBody, TableCell, TableHead, TableRow } from "@mui/material";
import { StyledTable } from "../../components/shared/Table.styled";
import { Character } from "../../types/character";

interface IProps {
  character: Character;
}


export const FundsContainer = ({ character }: IProps) => {

  if(character && character.items){
    const gold= character.items.gold ? character.items.gold.quantity : 0;
    const silver= character.items.silver ? character.items.silver.quantity : 0;
    const copper= character.items.copper ? character.items.copper.quantity : 0;
    return (
      <StyledTable>
        <Table>
          <TableHead>
            <TableRow >
              <TableCell>Gold</TableCell>
              <TableCell>{gold}</TableCell>
              <TableCell>Silver</TableCell>
              <TableCell>{silver}</TableCell>
              <TableCell>Copper</TableCell>
              <TableCell>{copper}</TableCell>
            </TableRow>
          </TableHead>
        </Table>
      </StyledTable>
    );
  } else {
    return <>No Character loaded!</>
  }
};