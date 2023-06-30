import { Fab, IconButton, Table, TableBody, TableCell, TableHead, TableRow } from "@mui/material";
import { StyledTable } from "../../components/shared/Table.styled";
import AddIcon from "@mui/icons-material/Add";
import Stack from '@mui/material/Stack';

interface IProps {
  // character: Character;
}


export const ItemsContainer = ({}: IProps) => {

  const itemRows = function(): any[] {

    const items = [];
    for (let i = 0; i < 15; i++) {
      items.push(<TableRow key={i}>
          <TableCell height={12}> </TableCell>
          <TableCell> </TableCell>
          <TableCell> </TableCell>
          <TableCell> </TableCell>
          <TableCell> </TableCell>
          <TableCell> </TableCell>
          <TableCell> </TableCell>
          <TableCell> </TableCell>
        </TableRow>
      );
    }
    return items;
  };


  return (
    <>
      <Stack direction="row-reverse" >
        <Fab color="success" size="small" aria-label="add">
          <AddIcon />
        </Fab>
      </Stack>
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
    </>

  );
};