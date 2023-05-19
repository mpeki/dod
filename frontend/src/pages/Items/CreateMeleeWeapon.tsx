import { useCallback, useState } from "react";
import { Item } from "../../types/item";
import { ItemType } from "../../types/item-type";
import { useForm } from "react-hook-form";
import { ItemService } from "../../services/item.service";
import { Button, MenuItem, TextField } from "@mui/material";
import Select from "@mui/material/Select";
import InputLabel from "@mui/material/InputLabel";
import FormControl from "@mui/material/FormControl";

interface IProps {
  onConfirm: any;
}

interface FormData {
  handGrip: string;
  strengthGroup: number;
  itemKey: string;
  damage: string;
  length: number;
  weight: number;
  breakingPoint: number;
  price: number;
}

const initialState: Item = {
  itemType: ItemType.values.MELEE_WEAPON,
  breakable: true,
  itemKey: "",
  price: 0,
  weight: 0,
  piecesForPrice: 0,
  bp: 0,
  damage: "",
  handGrip: "",
  length: 0,
  strengthGroup: 0,
  bowCast: 0,
  bepStorage: 0,
  maxThrowDistance: 0,
  rangeMultiplier: 0,
  rangeTrait: ""
};
export const CreateMeleeWeapon = ({ onConfirm }: IProps) => {

  const { getValues, register, formState: { errors }, handleSubmit, reset } = useForm<FormData>();

  const [itemData, setItemData] = useState<Item>(initialState);

  const submitHandler = useCallback(async () => {

    const itemPostData: Item = {
      itemType: ItemType.values.MELEE_WEAPON,
      itemKey: getValues("itemKey"),
      price: getValues("price"),
      weight: getValues("weight"),
      bp: getValues("breakingPoint"),
      damage: getValues("damage"),
      handGrip: getValues("handGrip"),
      length: getValues("length"),
      strengthGroup: getValues("strengthGroup")
    };
    await ItemService.createItem(itemPostData);
    setItemData(initialState);
    // fetchCharactersHandler();
    reset();
  }, [getValues, reset]);

  const onSubmit = handleSubmit(submitHandler);

  return (
    <form onSubmit={onSubmit}>
      <div>
        <TextField required {...register("itemKey", { required: true })} id="itemKey" label="Item Key"
                   variant={"filled"} />
      </div>
      <div>
        <FormControl fullWidth>
          <InputLabel id="hand-grip">Handgrip</InputLabel>
          <Select {...register("handGrip")}>
            <MenuItem value="ONE_HANDED">One hand</MenuItem>
            <MenuItem value="ONE_OR_TWO_HANDED">One or two hands</MenuItem>
            <MenuItem value="TWO_HANDED">Two Hands</MenuItem>
          </Select>
        </FormControl>
      </div>
      <div>
        <TextField required {...register("strengthGroup", { required: true })} id="strengthGroup" label="Strength Group"
                   variant={"filled"} type={"number"} />
      </div>
      <div>
        <TextField required {...register("damage", { required: true })} id="damage" label="Damage" variant={"filled"} />
      </div>
      <div>
        <TextField required {...register("length", { required: true })} id="length" label="Length" variant={"filled"}
                   type={"number"} />
      </div>
      <div>
        <TextField required {...register("weight", { required: true })} id="weight" label="Weight (BEP)" variant={"filled"}
                   type={"number"} inputProps={{ inputMode: 'numeric', step: '.1' }}/>
      </div>
      <div>
        <TextField required {...register("breakingPoint", { required: true })} id="breakingPoint" label="BV"
                   variant={"filled"} type={"number"} />
      </div>
      <div>
        <TextField required {...register("price", { required: true })} id="price" label="Price (in silver)"
                   variant={"filled"} type={"number"} />
      </div>
      <div>
        <Button type="submit">Create</Button>
        <Button onClick={onConfirm}>Cancel</Button>
      </div>
</form>
)};