import { Button, ButtonGroup, TextField, Typography, Grid } from "@mui/material";
import { ChangeEvent, useEffect, useReducer, useState } from "react";
import { useTranslation } from "react-i18next";
import { PaymentItem } from "../../types/item"

interface IProps {
  paymentHandler: any;
  handleClose: any;
  itemName: string
  goldOwned: number;
  silverOwned: number;
  copperOwned: number;
  silverPrice: number;
}

enum Currency {
  Gold = 'gold',
  Silver = 'silver',
  Copper = 'copper',
}

interface IPaymentState {
  silverPayed: number;
  goldPayed: number;
  copperPayed: number;
  payedTotalInSilver: number;
}

type PaymentAction = {
  type: Currency;
  value: number;
};

const paymentReducer = (state: IPaymentState, action: PaymentAction): IPaymentState => {
  switch (action.type) {
    case Currency.Silver:
      return {
        ...state,
        silverPayed: action.value,
        payedTotalInSilver: action.value + (10 * state.goldPayed) + (state.copperPayed / 10)
      };
    case Currency.Gold:
      return {
        ...state,
        goldPayed: action.value,
        payedTotalInSilver: state.silverPayed + (10 * action.value) + (state.copperPayed / 10)
      };
    case Currency.Copper:
      return {
        ...state,
        copperPayed: action.value,
        payedTotalInSilver: state.silverPayed + 10 * state.goldPayed + action.value / 10
      };
    default:
      return state;
  }
};

export const Payment = ({ handleClose, paymentHandler, itemName, goldOwned, silverOwned, copperOwned, silverPrice }: IProps) => {

  const initialPaymentState = {
    silverPayed: silverPrice,
    goldPayed: 0,
    copperPayed: 0,
    payedTotalInSilver: silverPrice
  };

  const { t } = useTranslation("items");

  const [state, dispatch] = useReducer(paymentReducer, initialPaymentState);

  const [gold, setGold] = useState<number>(goldOwned);
  const [silver, setSilver] = useState<number>(silverOwned);
  const [copper, setCopper] = useState<number>(copperOwned);

  const { silverPayed, goldPayed, copperPayed } = state
  const [payedTotalInSilver, setPayedTotalInSilver] = useState<number>(silverPrice)
  const [key, setKey ] = useState<string>(itemName)
  const [quantity, setQuantity ] = useState<number>(1)
  const paymentItem : PaymentItem = {gold: goldPayed, silver: silverPayed, copper: copperPayed }

  useEffect(() => {
    if (key !== itemName) {
      setSilver(silverOwned);
      setPayedTotalInSilver(silverPrice);
      setKey(itemName);
      setQuantity(1);
    }
  }, [key, itemName]);

  const handlePaymentChange = (event: ChangeEvent<HTMLInputElement | HTMLTextAreaElement>, currency: Currency): void => {
    if (event?.target?.value === "" && currency === Currency.Silver) {
      event.target.value = "" + state.silverPayed;
      return;
    }
    let payed: number = Number(event.target.value);
    dispatch({type: currency, value: payed});
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
  }

  const showPayment = ( amountOwned: number, currency: Currency, price: number): JSX.Element => {
    const currencyPayed = currency === 'silver' ? silverPayed : currency === 'gold' ? goldPayed : copperPayed;

    return (
      <Grid container direction={"row"} alignItems={"center"} >
        <Grid item marginLeft={-1}>
          <Typography alignItems={"center"} id="modal-modal-title" component="h6">
            {t("form.funds.leading.text", { amountOwned: amountOwned, currency: t(currency) })}
          </Typography>
        </Grid>
        <Grid item marginLeft={2} marginBottom={3}>
          <TextField
              // disabled={price === 0}
              label={t(currency)}
              value={currencyPayed}
              type="number"
              margin="none"
              variant={"standard"}
              InputLabelProps={{
                shrink: true
              }}
              InputProps={{ inputProps: { min: 0, max: amountOwned } }}
              onChange={event => handlePaymentChange(event, currency)}
              onFocus={event => {event.target.select()}}
          />
        </Grid>
        <Grid item>
          <Typography alignItems={"center"} id="modal-modal-title" component="h6">
            {t("form.funds.trailing.text", { remaining: (amountOwned - currencyPayed), currency: currency })}
          </Typography>
        </Grid>
      </Grid>
    );

  };

  const noBuy : boolean = silverPrice === 0 || state.payedTotalInSilver < (silverPrice * quantity)

  return (
    <Grid container padding={2} alignItems={"center"} columns={2} direction={"row"}>
      <Grid item marginLeft={2} >
        <TextField
          sx={{ width: 100 }}
          variant={"standard"}
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
          label={t("form.payment.quantity.header")}
        />
      </Grid>
      <Grid item marginLeft={2} marginTop={4}>
        <Typography margin={1} alignItems={"center"} id="modal-modal-title" component="h6">
          {t("form.total.payment.text", { price: silverPrice * quantity, due: (silverPrice * quantity) - state.payedTotalInSilver })}
        </Typography>
        <Typography margin={1} alignItems={"center"} id="modal-modal-title" component="h6">
          {t("form.payment.missing", {paymentMissing: silverPrice * quantity - state.payedTotalInSilver})}
        </Typography>
      </Grid>
      <Grid item>
        <Typography margin={3} alignItems={"center"} id="modal-modal-title" variant="h6" component="h3">
          {goldOwned > 0 && showPayment(goldOwned, Currency.Gold,  0)}
          {silverOwned > 0 && showPayment(silverOwned, Currency.Silver, silverPrice*quantity)}
          {copperOwned > 0 && showPayment(copperOwned, Currency.Copper, 0)}
        </Typography>
      </Grid>
      <Grid item>
        <ButtonGroup disableElevation variant="text">
          <Button variant="contained" color="success" disabled={noBuy} onClick={() => paymentHandler(paymentItem, quantity)}>
            {t("form.buy.button")}
          </Button>
          <Button variant="text" color="secondary" onClick={handleClose}>{t("form.cancel.button")}</Button>
        </ButtonGroup>
      </Grid>
    </Grid>
  );

};


