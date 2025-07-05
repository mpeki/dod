import {Grid, IconButton, List, ListItem, ListItemText, Table, TableBody, TableCell, TableHead, TableRow} from "@mui/material";
import {StyledTable} from "../../components/shared/Table.styled";
import AddIcon from "@mui/icons-material/Add";
import Stack from '@mui/material/Stack';
import {StyledList} from "../../components/shared/List.styled";
import {BaseTraitValue} from "../../types/character";
import useCharacterItems from "./useCharacterItems";
import {useTranslation} from "react-i18next";
import {RemoveCircleOutline} from "@mui/icons-material";
import React, {useContext, useState} from "react";
import {CharacterItem} from "../../types/item";
import {showSuccessSnackbar, showWarningSnackbar} from "../../utils/DODSnackbars";
import {Change, createChange} from "../../types/change";
import {useChangeService} from "../../services/change.service";
import CharacterContext from "../Character/CharacterContext";

export const ItemsContainer = () => {

    const {t} = useTranslation("items");
    const {doCharacterChange} = useChangeService();
    const charContext = useContext(CharacterContext);
    if (!charContext || !charContext.currentCharacter) {
        throw new Error("SkillContainer must be rendered within an ActivateCharContext.Provider");
    }
    const [changeData, setChangeData] = useState<Change>(createChange());

    const {currentCharacter, fetchCharHandler} = charContext;
    const baseTraitMap: Map<string, BaseTraitValue> = currentCharacter.baseTraits ? new Map(Object.entries(currentCharacter.baseTraits)) : new Map();
    const items = useCharacterItems(currentCharacter);

    const weightCarried = currentCharacter.weightCarried || 0;
    const ratioCarried = (weightCarried) / (baseTraitMap.get("STRENGTH")?.currentValue || 1);
    const color = ratioCarried <= 0.8 ? 'green' : ratioCarried > 0.8 && ratioCarried < 1 ? 'orange' : 'red';

    const removeItemHandler = (charItem: CharacterItem) => (event: React.MouseEvent<SVGSVGElement>) => {
        if (!currentCharacter) {
            showWarningSnackbar("Character is not defined");
            return;
        }
        const changePostData: Change = createChange("REMOVE_ITEM_INIT_COMPLETE", "Remove item", charItem.item.itemKey, charItem.itemName);
        doCharacterChange(currentCharacter, changePostData)
            .then(() => {
                if (currentCharacter.id !== undefined) {
                    fetchCharHandler(currentCharacter.id).then((character) => showSuccessSnackbar("Removed Weapon! "));
                }
            })
            .catch((e) => showWarningSnackbar((e as Error).message))
            .finally(() => {
                setChangeData(createChange());
            });
    };
    const itemRows = function (): any[] {
        const result: JSX.Element[] = [];
        items.forEach((charItem, key) => {
            let itemName = ""
            if (charItem.item.itemType === "COIN") return;
            if (charItem.item.itemType === "ARMOR") {
                itemName = t(`armor.${charItem?.item.itemKey}`);
            } else {
                itemName = t(charItem?.item.itemKey);
            }

            result.push(<TableRow key={charItem.item.id}>
                    <TableCell height={12} colSpan={5}>{itemName}</TableCell>
                    <TableCell>{charItem.item.weight}</TableCell>
                    <TableCell><RemoveCircleOutline fontSize={"small"} color={"error"} onClick={removeItemHandler(charItem)}/></TableCell>
                </TableRow>
            );
        });
        return result;
    };


    return (
        <>
            <Stack direction="row-reverse">
                <IconButton edge="start" aria-label="add item">
                    <AddIcon/>
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
                            <TableCell key={"item"} sx={{fontWeight: "bold"}} colSpan={5}>{t("headers.item")}</TableCell>
                            <TableCell key={"weight"} sx={{fontWeight: "bold"}} colSpan={2}>{t("headers.weight")}</TableCell>
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
