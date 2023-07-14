import {
  Box,
  Fab,
  IconButton,
  Paper,
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableRow,
  Typography
} from "@mui/material";
import { StyledTable } from "../../components/shared/Table.styled";
import Stack from "@mui/material/Stack";
import AddIcon from "@mui/icons-material/Add";
import { useEffect, useState } from "react";
import Modal from "@mui/material/Modal";
import { BuyWeapon } from "./BuyWeapon";
import { Character } from "../../types/character";
import { CharacterItem, Item } from "../../types/item";

interface IProps {
  character: Character;
  fetchCharHandler: () => void;
}


export const WeaponsContainer = ({ character, fetchCharHandler }: IProps) => {


    const [items, setItems] = useState<Map<string, Item>>(new Map());
    const [open, setOpen] = useState(false);
    const handleOpen = () => setOpen(true);
    const handleClose = () => setOpen(false);

    useEffect(() => {
      let itemJSON = localStorage.getItem("items");
      if (itemJSON !== null && character.items) {
        const items: Item[] = JSON.parse(itemJSON);
        const itemsMap = new Map(items.map((item) => [item.itemKey, item]));
        const charItems: Map<string, CharacterItem> = new Map(Object.entries(character.items));
        const filteredMap = new Map([...itemsMap].filter(([key, item]) => {
          let result = false;
          charItems.forEach((charItem) => {
            if(charItem.item.itemKey === key){
              result = true;
            }
          });
          return result;
        }));
        setItems(filteredMap);
      }
    }, [character.items]);

    const itemRows = () => {
      const result: JSX.Element[] = [];
      items.forEach((item, key) => {
        if(item.itemType !== "MELEE_WEAPON" && item.itemType !== "RANGED_WEAPON" && item.itemType !== "SHIELD") return;
        result.push(<TableRow key={item.id}>
          <TableCell height={12}>{item.itemKey}</TableCell>
          <TableCell></TableCell>
          <TableCell>{item.damage}</TableCell>
          <TableCell>{item.length}</TableCell>
          <TableCell>{item.bp}</TableCell>
          <TableCell></TableCell>
          <TableCell>{item.bowCast}</TableCell>
          <TableCell>{item.weight}</TableCell>
        </TableRow>);
      });
      return result;
    };

    return (
      <>
        <Stack direction="row-reverse">
          <IconButton edge="start" aria-label="add skill">
            <AddIcon onClick={handleOpen}/>
          </IconButton>
        </Stack>
        <StyledTable>
          <Table>
            <TableHead>
              <TableRow>
                <TableCell sx={{fontWeight: "bold"}}>Weapon/Shield</TableCell>
                <TableCell sx={{fontWeight: "bold"}}>Fv</TableCell>
                <TableCell sx={{fontWeight: "bold"}}>Dmg</TableCell>
                <TableCell sx={{fontWeight: "bold"}}>Vl</TableCell>
                <TableCell sx={{fontWeight: "bold"}}>Bv</TableCell>
                <TableCell sx={{fontWeight: "bold"}}>Abs</TableCell>
                <TableCell sx={{fontWeight: "bold"}}>Range</TableCell>
                <TableCell sx={{fontWeight: "bold"}}>Weight</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {itemRows()}
            </TableBody>
          </Table>
        </StyledTable>
        <Modal
          open={open}
          onClose={handleClose}
          aria-labelledby="modal-modal-title"
          aria-describedby="modal-modal-description"
          style={{ display: "flex", alignItems: "center", justifyContent: "center" }}
        >
          <Paper elevation={3} sx={{ width: 500, height: 500 }}>
            <Typography margin={3} alignItems={"center"} id="modal-modal-title" variant="h6" component="h3">
              Buy a new Weapon or Shield
            </Typography>
            <Box>
              <BuyWeapon onConfirm={handleClose} character={character} fetchCharHandler={fetchCharHandler}/>
            </Box>
          </Paper>
        </Modal>
      </>
    );
  }
;