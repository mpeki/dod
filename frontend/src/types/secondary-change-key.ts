import { PaymentItem } from "./item";

export interface SecondaryChangeKey {
  changeType: string;
  changeKey: string;
  changeItem?: PaymentItem;
}