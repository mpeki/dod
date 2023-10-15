import { Box, IconButton, Paper, Table, TableBody, TableCell, TableHead, TableRow, Typography } from "@mui/material";
import { StyledTable } from "../../components/shared/Table.styled";
import Stack from "@mui/material/Stack";
import AddIcon from "@mui/icons-material/Add";
import { useEffect, useState } from "react";
import Modal from "@mui/material/Modal";
import { BuyWeapon } from "./BuyWeapon";
import { Character } from "../../types/character";
import { CharacterItem, Item } from "../../types/item";
import { CharacterState } from "../../types/character-state";
import { useTranslation } from "react-i18next";

interface IProps {
  character: Character;
  fetchCharHandler: (charId: string) => Promise<Character>;
}


export const WeaponsContainer = ({ character, fetchCharHandler }: IProps) => {

    const { t } = useTranslation(["char", "items"]);
    const [items, setItems] = useState<Map<string, Item>>(new Map());
    const [open, setOpen] = useState(false);
    const handleOpen = () => setOpen(true);
    const handleClose = () => setOpen(false);

    const canBuy: boolean = (character.state === CharacterState.READY_TO_PLAY);

    useEffect(() => {
      let itemJSON = localStorage.getItem("items");
      if (itemJSON !== null && character.items) {
        const items: Item[] = JSON.parse(itemJSON);
        const itemsMap = new Map(items.map((item) => [item.itemKey, item]));
        const charItems: Map<string, CharacterItem> = new Map(Object.entries(character.items));
        const filteredMap = new Map([...itemsMap].filter(([key, item]) => {
          let result = false;
          charItems.forEach((charItem) => {
            if (charItem.item.itemKey === key) {
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
        if (item.itemType !== "MELEE_WEAPON" && item.itemType !== "RANGED_WEAPON" && item.itemType !== "SHIELD") return;
        result.push(<TableRow key={item.id}>
          <TableCell height={12}>{t(`items:${item.itemKey}`)}</TableCell>
          <TableCell></TableCell>
          <TableCell>{item.damage}</TableCell>
          <TableCell>{item.length}</TableCell>
          <TableCell>{item.bp}</TableCell>
          <TableCell></TableCell>
          <TableCell>{item.bowCast}</TableCell>
          <TableCell>{item.weight}</TableCell>
        </TableRow>);
      });
      // Add empty rows to fill the table - if we are printing, we need 10 rows, otherwise 3
/*
      let emptyRows = isPrinting ? 5 - result.length : 3 - result.length < 1 ? 0 : 3 - result.length
      for(let i = 0; i < emptyRows; i++) {
        result.push(<TableRow key={i} style={{ height: "25px" }}>
          <TableCell></TableCell>
          <TableCell></TableCell>
          <TableCell></TableCell>
          <TableCell></TableCell>
          <TableCell></TableCell>
          <TableCell></TableCell>
          <TableCell></TableCell>
          <TableCell></TableCell>
        </TableRow>);
      }
*/


      return result;
    };

    return (
      <>
        <Stack direction="row-reverse">
          <IconButton edge="start" aria-label="add skill" disabled={!canBuy} onClick={handleOpen}>
            <AddIcon />
          </IconButton>
        </Stack>
        <StyledTable>
          <Table>
            <TableHead>
              <TableRow>
                <TableCell sx={{ fontWeight: "bold" }}>{t("char:detail.weapons.header.item")}</TableCell>
                <TableCell sx={{ fontWeight: "bold" }}>{t("char:detail.weapons.header.fv")}</TableCell>
                <TableCell sx={{ fontWeight: "bold" }}>{t("char:detail.weapons.header.damage")}</TableCell>
                <TableCell sx={{ fontWeight: "bold" }}>{t("char:detail.weapons.header.wLength")}</TableCell>
                <TableCell sx={{ fontWeight: "bold" }}>{t("char:detail.weapons.header.breakPoints")}</TableCell>
                <TableCell sx={{ fontWeight: "bold" }}>{t("char:detail.weapons.header.absorption")}</TableCell>
                <TableCell sx={{ fontWeight: "bold" }}>{t("char:detail.weapons.header.range")}</TableCell>
                <TableCell sx={{ fontWeight: "bold" }}>{t("char:detail.weapons.header.weight")}</TableCell>
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
              <BuyWeapon onConfirm={handleClose} character={character} fetchCharHandler={fetchCharHandler} />
            </Box>
          </Paper>
        </Modal>
      </>
    );
  }
;