import { useCallback, useContext, useEffect, useState } from "react";
import { useItemService } from "../../services/item.service";
import { Item } from "../../types/item";
import { Paper } from "@mui/material";
import { Character } from "../../types/character";
import Stack from "@mui/material/Stack";
import { Payment } from "./Payment";
import { Change, createChange } from "../../types/change";
import { useChangeService } from "../../services/change.service";
import { ItemSelector } from "./ItemSelector";
import { showWarningSnackbar } from "../../utils/DODSnackbars";
import CharacterContext from "../Character/CharacterContext";

interface IProps {
  onConfirm: any;
}

export const BuyWeapon = ({ onConfirm }: IProps) => {

  const charContext = useContext(CharacterContext);
  if (!charContext || !charContext.currentCharacter) {
    throw new Error("SkillContainer must be rendered within an ActivateCharContext.Provider");
  }

  const { currentCharacter, fetchCharHandler } = charContext;

  const orgGold = currentCharacter?.items?.gold ? currentCharacter.items.gold.quantity : 0;
  const orgSilver = currentCharacter?.items?.silver ? currentCharacter.items.silver.quantity : 0;
  const orgCopper = currentCharacter?.items?.copper ? currentCharacter.items.copper.quantity : 0;

  const { doChange } = useChangeService();
  const { getMeleeWeapons } = useItemService();

  const [items, setItems] = useState<Item[]>([]);
  const [inputValue, setInputValue] = useState("");
  const [itemSelected, setItemSelected] = useState<Item>();
  const [gold, setGold] = useState<number>(orgGold);
  const [silver, setSilver] = useState<number>(orgSilver);
  const [copper, setCopper] = useState<number>(orgCopper);
  const [owed, setOwed] = useState<number>(0);

  const [changeData, setChangeData] = useState<Change>(createChange());

  const fetchItemsHandler = useCallback(async () => {
    await getMeleeWeapons()
    .then((items) => {
      setItems(items);
    })
    .catch((e) => showWarningSnackbar((e as Error).message));
  }, [getMeleeWeapons]);

  const doPaymentRequest = useCallback(async () => {
    if (itemSelected) {
      const changeType = currentCharacter?.state === "INIT_COMPLETE" ? "NEW_ITEM_INIT_COMPLETE" : "NEW_ITEM";
      const changePostData: Change = createChange(changeType, "Buy new item", itemSelected.itemKey, 1);
      if ( currentCharacter.id != null ) {
        await doChange(currentCharacter.id, changePostData).then(() => {
            setChangeData(createChange());
            fetchCharHandler(currentCharacter.id as string);
            onConfirm();
          }
        );
      }
    }
    onConfirm();
  }, [itemSelected, onConfirm, currentCharacter.id, fetchCharHandler, doChange]);

  const resetFunds = () => {
    setGold(orgGold);
    setSilver(orgSilver);
    setCopper(orgCopper);
  };

  useEffect(() => {
    fetchItemsHandler().then();
  }, [fetchItemsHandler]);

  const handleItemChange = (items: Item[], newInputValue: string) => {
    setInputValue(newInputValue);
    setItemSelected(items.find(item => item.itemKey === newInputValue));
    setOwed(items.find(item => item.itemKey === newInputValue)?.price || 0);
    resetFunds();
  }


  return (
    <Paper elevation={3}>
      <Stack>
        <ItemSelector label="Select a Weapon" items={items} onChange={handleItemChange} />
        <Payment handleClose={onConfirm} paymentHandler={doPaymentRequest} itemName={itemSelected ? itemSelected.itemKey : "none"}
                 goldOwned={orgGold} silverOwned={orgSilver} copperOwned={orgCopper}
                 silverPrice={itemSelected ? itemSelected.price : owed} />
      </Stack>
    </Paper>
  )
    ;
};