import { Character } from "./character";
import { SecondaryChangeKey } from "./secondary-change-key";

export interface Change {
  changeType: string;
  changeDescription: string;
  changeKey: string;
  secondaryChangeKey?: SecondaryChangeKey;
  modifier: any;
  objectAfterChange?: Character;
}