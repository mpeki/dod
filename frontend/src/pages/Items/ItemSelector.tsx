import { Box, FormControl, InputLabel, MenuItem, Select } from "@mui/material";
import { Item } from "../../types/item";
import { useState } from "react";
import { useTranslation } from "react-i18next";
import { getPriceForSize, getWeightForSize } from "../../utils/BuyItemHelper";
import { BaseTraitValue } from "../../types/character";

interface IProps {
  items: Item[];
  itemType?: string;
  charSize?: BaseTraitValue;
  onChange: (
    items: Item[],
    input: string
  ) => void;
  label: string;
}

export const ItemSelector = ({
                               items,
                               itemType,
                               onChange,
                               label,
                               charSize = { currentValue: 0, startValue: 0, groupValue: 0 }
                             }: IProps) => {
  const { t } = useTranslation(["items", "char"]);
  const [selectedValue, setSelectedValue] = useState("");
    console.log("items: ", items);
  const itemOptions = items
  .map((item, index) => ({
    id: item.id,
    name: item.itemKey,
    itemKey: item.itemKey,
    strengthGroup: item.strengthGroup,
    damage: item.damage,
    itemType: item.itemType,
    price: item.price,
    weight: item.weight,
    weightReference: item.weightReference,
    length: item.length,
    bepStorage: item.bepStorage,
    bp: item.bp,
    handGrip: item.handGrip,
    label: t(itemType ? `items:${itemType.toLowerCase()}.${item.itemKey}` : `items:${item.itemKey}`)
  }));
  return (
    <FormControl fullWidth variant="standard" sx={{ minWidth: 300 }}>
      <InputLabel id="select-label">{items.length > 0 ? label : ""}</InputLabel>
      <Select
        labelId="select-label"
        id="select"
        value={selectedValue}
        onChange={(event) => {
          const newValue = event.target.value;
          onChange(items, newValue);
          setSelectedValue(newValue);
        }}
        displayEmpty
      >
        {items.length > 0 ? (itemOptions.map((itemOption) => (
          <MenuItem key={itemOption.id} value={itemOption.name}>
            <Box component="span" sx={{ "& > img": { mr: 2, flexShrink: 0 } }}>
              {itemOption.label} (🪙: { getPriceForSize(itemOption, charSize)} sp,
              ⚖️: {itemOption.weight ? itemOption.weight : (itemOption.weightReference ? getWeightForSize(charSize, itemOption.weightReference) || "?" : "?" )} bep)
            </Box>
          </MenuItem>
        ))
        ) : (
            <MenuItem disabled value="">
                <em>{t("char:detail.body.filters.no.items")}</em>
            </MenuItem>
        )}
      </Select>
    </FormControl>
  );
};