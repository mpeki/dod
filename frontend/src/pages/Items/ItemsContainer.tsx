import {
  Fab, Grid, IconButton,
  List,
  ListItem,
  ListItemText,
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableRow, Typography
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
  const weightCarried = character.weightCarried || 0;
  const ratioCarried = (weightCarried) / (baseTraitMap.get("STRENGTH")?.currentValue || 1);
  const color = ratioCarried <= 0.8 ? 'green' : ratioCarried > 0.8 && ratioCarried < 1 ?  'orange' : 'red';
  const itemRows = function(): any[] {

    const items = [];
    for (let i = 0; i < 3; i++) {
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
      <Stack direction="row-reverse">
        <IconButton edge="start" aria-label="add item">
          <AddIcon />
        </IconButton>
      </Stack>
      <Grid container direction={"row"}>
        <Grid item justifyContent={"left"}>
          <List dense={true}>
            <StyledList>
              <ListItem dense={true}>
                <ListItemText primary="Total Weight Carried (Capacity)"
                              secondary={`${weightCarried.toFixed(2)} BEP (${baseTraitMap.get("STRENGTH")?.currentValue} BEP)` || "N/A"}
                              secondaryTypographyProps={{
                                color: color,
                              }}/>
              </ListItem>
            </StyledList>
          </List>
        </Grid>
      </Grid>
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