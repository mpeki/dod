import { Box, IconButton, Paper, Table, TableBody, TableCell, TableHead, TableRow, Typography } from "@mui/material";
import { StyledTable } from "../../components/shared/Table.styled";
import Stack from "@mui/material/Stack";
import AddIcon from "@mui/icons-material/Add";
import { useContext, useEffect, useState } from "react";
import Modal from "@mui/material/Modal";
import { BuyWeapon } from "./BuyWeapon";
import { CharacterItem, Item } from "../../types/item";
import { CharacterState } from "../../types/character-state";
import { useTranslation } from "react-i18next";
import CharacterContext from "../Character/CharacterContext";
import { showWarningSnackbar } from "../../utils/DODSnackbars";

export const WeaponsContainer = () => {

    const { t } = useTranslation(["char", "items"]);
    const [items, setItems] = useState<Map<string, CharacterItem>>(new Map());
    const [open, setOpen] = useState(false);
    const charContext = useContext(CharacterContext);
    if (!charContext || !charContext.currentCharacter) {
      throw new Error("SkillContainer must be rendered within an ActivateCharContext.Provider");
    }

    const { currentCharacter } = charContext;

  if (currentCharacter == null) {
    showWarningSnackbar("No character selected!");
  }

  useEffect(() => {
    let itemJSON = localStorage.getItem("items");
    if (itemJSON !== null && currentCharacter && currentCharacter.items) {
      const items: Item[] = JSON.parse(itemJSON);
      const itemsMap = new Map(items.map((item) => [item.itemKey, item]));
      const charItems: Map<string, CharacterItem> = new Map(Object.entries(currentCharacter.items));

      // Iterate through each charItem in charItems
      charItems.forEach((charItem, key) => {
        // Look up the corresponding item from itemsMap using the itemKey from charItem.item
        const matchingItem = itemsMap.get(charItem.item.itemKey);
        if (matchingItem) {
          // Assign the matching item to charItem.item
          charItem.item = matchingItem;
        }
      });

      // Now charItems has been updated with the relevant items from itemsMap
      // console.log("Updated charItems", charItems);

/*
      // Optionally, update the state or perform other actions with the updated charItems
      // For example:
      setCurrentCharacter(prevState => ({
        ...prevState,
        items: Object.fromEntries(charItems),
      }));
*/
      setItems(charItems);
    }
  }, [currentCharacter]);

    if (currentCharacter == null) {
      return <></>;
    }

    const canBuy: boolean = (currentCharacter.state === CharacterState.READY_TO_PLAY);

    const handleOpen = () => setOpen(true);
    const handleClose = () => setOpen(false);

    const itemRows = () => {
      const result: JSX.Element[] = [];
      items.forEach((charItem, key) => {
        if (charItem.item.itemType !== "MELEE_WEAPON" && charItem.item.itemType !== "RANGED_WEAPON" && charItem.item.itemType !== "SHIELD") return;
        console.log("item", charItem);
        result.push(<TableRow key={charItem.itemName}>
          <TableCell height={12}>{t(`items:${charItem.item.itemKey}`)}</TableCell>
          <TableCell></TableCell>
          <TableCell>{charItem.item.damage}</TableCell>
          <TableCell>{charItem.item.length}</TableCell>
          <TableCell>{charItem.item.bp}</TableCell>
          <TableCell></TableCell>
          <TableCell>{charItem.item.bowCast}</TableCell>
          <TableCell>{charItem.item.weight}</TableCell>
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
              <BuyWeapon onConfirm={handleClose} />
            </Box>
          </Paper>
        </Modal>
      </>
    );
  }
;