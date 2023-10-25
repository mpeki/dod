import { Character } from "../../../../types/character";
import { StyledTable } from "../../../../components/shared/Table.styled";
import { Table, TableBody, TableCell, TableHead, TableRow } from "@mui/material";

interface ICharacterProps {
  character: Character;
}

export const PrintHeroDetails = ({ character }: ICharacterProps) => {

  const heroDeedRows = () => {
    const result: JSX.Element[] = [];
    let emptyRows = 8;
    for(let i = 0; i < emptyRows; i++) {
      result.push(<TableRow key={i} style={{ height: "22px" }}>
        <TableCell sx={{borderBottomColor: 'darkgray'}}></TableCell>
      </TableRow>);
    }
    return result;
  };


  return (
      <StyledTable >
        <Table>
          <TableHead>
            <TableRow style={{ height: "22px" }}>
              <TableCell sx={{ borderBottomColor: 'darkgray', fontWeight: 'bold' }}>Heltegerninger</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {heroDeedRows()}
          </TableBody>
        </Table>
      </StyledTable>
    );
}