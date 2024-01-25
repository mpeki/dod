import { useCallback, useContext, useEffect, useState } from "react";
import { useItemService } from "../../services/item.service";
import { Item } from "../../types/item";
import {FormControlLabel, FormGroup, Grid, Typography} from "@mui/material";
import { Payment } from "./Payment";
import { Change, createChange } from "../../types/change";
import { useChangeService } from "../../services/change.service";
import { ItemSelector } from "./ItemSelector";
import { showWarningSnackbar } from "../../utils/DODSnackbars";
import CharacterContext from "../Character/CharacterContext";
import { useTranslation } from "react-i18next";
import { BaseTraitValue } from "../../types/character";
import { SecondaryChangeKey } from "../../types/secondary-change-key";
import { PaymentItem } from "../../types/item"
import { getPriceForSize } from "../../utils/BuyItemHelper";
import {ArmorFilters, FilterIProps} from "./ArmorFilters";


interface IProps {
  itemType: string;
  charSize?: BaseTraitValue;
  onConfirm: any;
  filtersComponent?: React.ComponentType<FilterIProps>;
  t: (key: string) => string;
}

export const BuyItem = ({ itemType, charSize, onConfirm, filtersComponent: FiltersComponent, t }: IProps) => {


  const charContext = useContext(CharacterContext);
  if (!charContext || !charContext.currentCharacter) {
    throw new Error("SkillContainer must be rendered within an ActivateCharContext.Provider");
  }

  const { currentCharacter, fetchCharHandler } = charContext;

  const orgGold = currentCharacter?.items?.gold ? currentCharacter.items.gold.quantity : 0;
  const orgSilver = currentCharacter?.items?.silver ? currentCharacter.items.silver.quantity : 0;
  const orgCopper = currentCharacter?.items?.copper ? currentCharacter.items.copper.quantity : 0;

  const { doChange } = useChangeService();
  const { getItemsByType } = useItemService();

  const [items, setItems] = useState<Item[]>([]);
  const [inputValue, setInputValue] = useState("");
  const [itemSelected, setItemSelected] = useState<Item>();
  const [gold, setGold] = useState<number>(orgGold);
  const [silver, setSilver] = useState<number>(orgSilver);
  const [copper, setCopper] = useState<number>(orgCopper);
  const [owed, setOwed] = useState<number>(0);
  const [filteredItems, setFilteredItems] = useState<Item[]>([]);
  const [changeData, setChangeData] = useState<Change>(createChange());

  const fetchItemsHandler = useCallback(async () => {
    await getItemsByType(itemType)
    .then((items) => {
      setItems(items);
    })
    .catch((e) => showWarningSnackbar((e as Error).message));
  }, [getItemsByType]);

  const doPaymentRequest = useCallback(async (paymentItem: PaymentItem, numItems: number) => {
    if (itemSelected) {
      const changeType = currentCharacter?.state === "INIT_COMPLETE" ? "NEW_ITEM_INIT_COMPLETE" : "NEW_ITEM";
      const secondaryChange: SecondaryChangeKey = { changeType: "NEW_ITEM_PAYMENT", changeKey: '', changeItem: paymentItem
      };
      const changePostData: Change = createChange(changeType, "Buy new item", itemSelected.itemKey, secondaryChange, numItems);
      if (currentCharacter.id != null) {
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
  };
  const label = { inputProps: { 'aria-label': 'Checkbox demo' } };

  return (
      <Grid container>
        <Grid item paddingLeft={4}>
          {FiltersComponent && <FiltersComponent items={items} setFilteredItems={setFilteredItems} t={t} />}
          <ItemSelector label={t(`buy.${itemType}.selectPlaceholder`)} items={filteredItems} charSize={charSize} onChange={handleItemChange}
                        itemType={itemType} />
        </Grid>
        <Grid item>
        <Payment handleClose={onConfirm} paymentHandler={doPaymentRequest}
                 itemName={itemSelected ? itemSelected.itemKey : "none"}
                 goldOwned={orgGold} silverOwned={orgSilver} copperOwned={orgCopper}
                 silverPrice={itemSelected ? getPriceForSize(itemSelected, charSize) : owed} />
        </Grid>
      </Grid>
  )
    ;
};