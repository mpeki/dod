import { useCallback, useEffect, useState } from "react";
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

interface IProps {
  onConfirm: any;
  fetchCharHandler: () => void;
  character: Character;
}

export const BuyWeapon = ({ onConfirm, character, fetchCharHandler }: IProps) => {

  const orgGold = character?.items?.gold ? character.items.gold.quantity : 0;
  const orgSilver = character?.items?.silver ? character.items.silver.quantity : 0;
  const orgCopper = character?.items?.copper ? character.items.copper.quantity : 0;

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
      console.log(items);
      setItems(items);
    })
    .catch((e) => showWarningSnackbar((e as Error).message));
  }, []);

  const doPaymentRequest = useCallback(async () => {
    if (itemSelected) {
      const changePostData: Change = createChange("NEW_ITEM", "Buy new item", itemSelected.itemKey, 1);
      if (character.id != null) {
        await doChange(character.id, changePostData);
      }
      setChangeData(createChange());
      fetchCharHandler();
      onConfirm();
    }
    onConfirm();
  }, [fetchCharHandler, changeData, character.id, itemSelected, onConfirm]);

  const resetFunds = () => {
    setGold(orgGold);
    setSilver(orgSilver);
    setCopper(orgCopper);
  };

  useEffect(() => {
    fetchItemsHandler().then();
  }, [fetchItemsHandler]);

  const itemOptions = items
  .map((item, index) => ({
    id: index + 1,
    name: item.itemKey,
    strengthGroup: item.strengthGroup,
    damage: item.damage,
    itemType: item.itemType,
    price: item.price,
    weight: item.weight,
    length: item.length,
    bepStorage: item.bepStorage,
    bp: item.bp,
    handGrip: item.handGrip
  }));

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
{/*
        <Autocomplete
          fullWidth
          disablePortal
          inputValue={inputValue}
          onInputChange={(event, newInputValue) => {
            console.log("input changed " + newInputValue);
            setInputValue(newInputValue);
            setItemSelected(items.find(item => item.itemKey === newInputValue));
            setOwed(items.find(item => item.itemKey === newInputValue)?.price || 0);
            resetFunds();
          }}
          id="combo-box-demo"
          options={itemOptions}
          sx={{ width: 300 }}
          autoHighlight
          getOptionLabel={(option) => option.name}
          renderOption={(props, itemOption) => (
            <Box component="li" sx={{ "& > img": { mr: 2, flexShrink: 0 } }} {...props}>
              {itemOption.name} (cost: {itemOption.price} sp, weight: {itemOption.weight} bep)
            </Box>
          )}
          isOptionEqualToValue={(item, value) => item.name === value.name}
          renderInput={(params) => <TextField {...params} label="Select a weapon"
                                              inputProps={{
                                                ...params.inputProps,
                                                autoComplete: "new-password" // disable autocomplete and autofill
                                              }}
          />}
        />
*/}
        <Payment handleClose={onConfirm} paymentHandler={doPaymentRequest} itemName={itemSelected ? itemSelected.itemKey : "none"} goldOwned={orgGold} silverOwned={orgSilver} copperOwned={orgCopper}
                 silverPrice={itemSelected ? itemSelected.price : owed} />
      </Stack>
    </Paper>
  )
    ;
};