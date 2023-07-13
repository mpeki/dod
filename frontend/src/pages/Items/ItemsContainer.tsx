import {
  Fab, Grid,
  List,
  ListItem,
  ListItemText,
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableRow
} from "@mui/material";
import { StyledTable } from "../../components/shared/Table.styled";
import AddIcon from "@mui/icons-material/Add";
import Stack from '@mui/material/Stack';
import { StyledList } from "../../components/shared/List.styled";
import { BaseTraitValue, Character } from "../../types/character";

interface IProps {
  character: Character;
}


export const ItemsContainer = ({character}: IProps) => {

  const baseTraitMap: Map<string, BaseTraitValue> = character.baseTraits ? new Map(Object.entries(character.baseTraits)) : new Map();

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
      <Stack direction={"row"}>
        <Grid container>
          <Grid item justifyContent={"left"}>
            <List dense={true}>
              <StyledList>
                <ListItem dense={true}>
                  <ListItemText primary={"Total Weight Carried (Capacity)"}
                                secondary={`${character.weightCarried} BEP (${baseTraitMap.get("STRENGTH")?.currentValue} BEP)` || "N/A"} />
                </ListItem>
              </StyledList>
            </List>
          </Grid>
        </Grid>
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