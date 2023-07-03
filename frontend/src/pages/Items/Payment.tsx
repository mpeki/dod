import { Stack, Button, ButtonGroup, Divider, TextField, Typography } from "@mui/material";
import { ChangeEvent, useState } from "react";

interface IProps {
  paymentHandler: any;
  handleClose: any;
  itemName: string
  goldOwned: number;
  silverOwned: number;
  copperOwned: number;
  silverPrice: number;
}

export const Payment = ({ handleClose, paymentHandler, itemName, goldOwned, silverOwned, copperOwned, silverPrice }: IProps) => {

  const [gold, setGold] = useState<number>(goldOwned);
  const [silver, setSilver] = useState<number>(silverOwned);
  const [copper, setCopper] = useState<number>(copperOwned);

  const [silverPayed, setSilverPayed ] = useState<number>(silverPrice);
  const [key, setKey ] = useState<string>(itemName)
  const [quantity, setQuantity ] = useState<number>(1)

  if(key !== itemName){
    console.log("resetting state!")
    setSilver(silverOwned);
    setSilverPayed(silverPrice);
    setKey(itemName)
    setQuantity(1)
  }


  const handlePaymentChange = (event: ChangeEvent<HTMLInputElement | HTMLTextAreaElement>, currency: string): void => {
    if (event.target.value === "") {
      event.target.value = "" + silverPayed;
      return;
    }
    let payed: number = parseInt(event.target.value);
    setSilverPayed(payed);
  }

  const handleQuantityChange = (event: ChangeEvent<HTMLInputElement | HTMLTextAreaElement>): void => {
    if (event.target.value === "") {
      event.target.value = "" + quantity;
      return;
    }
    let q = parseInt(event.target.value)
    if(q*silverPrice > silverOwned){
      return;
    }
    setQuantity(q)
    setSilverPayed(silverPrice*q);
  }

  const showPayment = ( amountOwned: number, currency: string, silverPrice: number): JSX.Element => {
    return (
      <Stack direction={"row"} alignItems={"center"} >
        {amountOwned} {currency}, use
        <TextField
          disabled={silverPrice === 0}
          label={currency}
          value={silverPayed}
          type="number"
          margin="none"
          variant={"outlined"}
          InputLabelProps={{
            shrink: true
          }}
          InputProps={{ inputProps: { min: 0, max: amountOwned } }}
          onChange={event => handlePaymentChange(event, currency)}
          onFocus={event => {event.target.select()}}
        />, leaves you with {amountOwned - silverPayed}
      </Stack>
    );

  };

  return (
    <>
      <Divider variant="middle">Quantity to buy</Divider>
      <TextField
        disabled={itemName === 'none'}
        value={quantity}
        type="number"
        margin="normal"
        InputLabelProps={{
          shrink: true
        }}
        onChange={event => handleQuantityChange(event)}
        inputProps={{ inputMode: "numeric", pattern: "[0-9]*" }}
        InputProps={{ inputProps: { min: 1, max: 10 } }}
      />
      <Divider variant="middle">Payment Due</Divider>
      <Typography margin={3} alignItems={"center"} id="modal-modal-title" variant="h6" component="h4">
        Price: {silverPrice * quantity} silver, missing payment is: {(silverPrice*quantity) - silverPayed} silver
      </Typography>
      <Divider variant="middle">Available Funds</Divider>
      <Typography margin={3} alignItems={"center"} id="modal-modal-title" variant="h6" component="h3">
        {goldOwned > 0 && showPayment(goldOwned, "Gold", silverPrice)}
        {silverOwned > 0 && showPayment(silverOwned, "Silver", silverPrice*quantity)}
        {copperOwned > 0 && showPayment(copperOwned, "Copper", silverPrice)}
      </Typography>
      <ButtonGroup variant="contained" aria-label="outlined primary button group">
        <Button variant="text" color="secondary" onClick={handleClose}>Cancel</Button>
        <Button variant="contained" color="success" disabled={silverPrice === 0 || silverPayed !== (silverPrice * quantity)} onClick={paymentHandler}>
          Buy
        </Button>
      </ButtonGroup>
    </>
  );

};


