import { BodyPartValue } from "../../types/character";
import { BodyPartItem } from "./BodyPart";
import { StyledTable } from "../../components/shared/Table.styled";
import {
  IconButton,
  List,
  ListItem,
  ListItemText,
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableRow
} from "@mui/material";
import { StyledList } from "../../components/shared/List.styled";
import AddIcon from "@mui/icons-material/Add";
import { useTranslation } from "react-i18next";
import { CharacterState } from "../../types/character-state";
import React, { useContext, useState } from "react";
import Modal from "@mui/material/Modal";
import CharacterContext from "./CharacterContext";
import { BuyItem } from "../Items/BuyItem";
import {CharacterItem} from "../../types/item";
import classes from "./AddCharacter.module.css";
import { getValueOfTrait } from "../../utils/CharacterHelper";
import {ArmorFilters} from "../Items/ArmorFilters";

interface IProps {
  parts: Record<string, BodyPartValue> | undefined;
  items: Map<string, CharacterItem>;
}

function findArmorByBodyPart(itemsMap: Map<string, CharacterItem>, bodyPart: string): CharacterItem | undefined {
  for (let [key, value] of itemsMap.entries()) {
    const item = value.item;
    if (item.itemType === 'ARMOR' && item.bodyPartsCovered?.includes(bodyPart)) {
      return value;
    }
  }

  return undefined;
}

export const BodyContainer = ({ parts, items }: IProps): JSX.Element => {
  const [open, setOpen] = useState(false);
  const { t } = useTranslation("char");
  const charContext = useContext(CharacterContext);

  if (!charContext || !charContext.currentCharacter) {
    throw new Error("SkillContainer must be rendered within an ActivateCharContext.Provider");
  }

  const { currentCharacter, fetchCharHandler } = charContext;
  const handleOpen = () => setOpen(true);
  const handleClose = () => setOpen(false);


  if (parts) {
    const bodyPartMap: Map<string, BodyPartValue> = new Map(Object.entries(parts));

    const bodyParts = function(): any[] {
      const bodyParts = [];
      for (let [key, value] of bodyPartMap) {
        if (key === "TOTAL") continue;
        const armorPiece: CharacterItem | undefined = findArmorByBodyPart(items, key);
        bodyParts.push(<BodyPartItem key={key} bodyPartName={key} bodyPartValue={value} item={armorPiece} />);
      }
      return bodyParts;
    };

    const canBuy: boolean = (currentCharacter.state === CharacterState.INIT_COMPLETE);

    return (
      <>
        <List dense={true}>
          <StyledList>
            <ListItem dense={true}>
              <ListItemText primary={t("detail.body.totalHP")} secondary={parts["TOTAL"].maxHP} />
            </ListItem>
            <IconButton sx={{ mr: 2 }} aria-label="add skill" disabled={!canBuy} onClick={handleOpen}
                        title={t("char:detail.body.armor.addText")}>
              <AddIcon />
            </IconButton>
          </StyledList>
        </List>

        <StyledTable>
          <Table>
            <TableHead>
              <TableRow>
                <TableCell colSpan={3} align="center">{t("detail.body.header.hp")}</TableCell>
                <TableCell colSpan={4} align="justify">{t("detail.body.header.armor")}</TableCell>
              </TableRow>
            </TableHead>
            <TableHead>
              <TableRow>
                <TableCell sx={{ fontWeight: "bold" }}>{t("detail.body.header.bodyPart")}</TableCell>
                <TableCell sx={{ fontWeight: "bold" }}>{t("detail.body.header.maxHP")}</TableCell>
                <TableCell sx={{ fontWeight: "bold" }}>{t("detail.body.header.currentHP")}</TableCell>
                <TableCell sx={{ fontWeight: "bold" }}>{t("detail.body.header.armorMat")}</TableCell>
                <TableCell sx={{ fontWeight: "bold" }}>{t("detail.body.header.abs")}</TableCell>
                <TableCell sx={{ fontWeight: "bold" }}>{t("detail.body.header.weight")}</TableCell>
                <TableCell sx={{ fontWeight: "bold" }}>{t("detail.body.header.penalty")}</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {bodyParts()}
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
          <div>
            <div className={classes.backdrop} onClick={handleClose}></div>
            <div className={classes.modal}>
              <header className={classes.header}>
                <h2>{t("char:detail.body.armor.addText")}</h2>
              </header>
              <div className={classes.content}>
                <BuyItem itemType={"ARMOR"}
                         charSize={getValueOfTrait(currentCharacter, "SIZE")}
                         onConfirm={handleClose} filtersComponent={ArmorFilters} t={t}/>
              </div>
              <footer className={classes.actions}>
              </footer>
            </div>
          </div>

        </Modal>

      </>
    );
  } else return (<></>);
};