import { Autocomplete, Box, TextField } from "@mui/material";
import { Item } from "../../types/item";
import { useState } from "react";

interface IProps {
  items: Item[];
  onChange: (items: Item[], input: string) => void;
  label: string;
}

export const ItemSelector = ({ items, onChange, label }: IProps) => {

  const [inputValue, setInputValue] = useState("");

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

  return (
    <Autocomplete
      fullWidth
      disablePortal
      inputValue={inputValue}
      onInputChange={(event, newInputValue) => {
        onChange(items, newInputValue);
        setInputValue(newInputValue);
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
      renderInput={(params) => <TextField label={label}
                                          inputProps={{
                                            ...params.inputProps,
                                            autoComplete: "off" // disable autocomplete and autofill
                                          }}
      />}
    />
  )
}