import { Character } from "./character";

export interface Item {
  id?: string;
  itemType: string;
  breakable?: boolean;
  itemKey: string;
  abs?: number;
  bodyPartsCovered?: string[];
  coverage?: number;
  dodgePenalty?: number;
  price: number;
  weight?: number;
  weightReference?: string;
  piecesForPrice?: number;
  bp: number;
  damage?: string;
  handGrip?: string;
  length?: number;
  strengthGroup?: number;
  bowCast?: number;
  bepStorage?: number;
  maxThrowDistance?: number;
  rangeMultiplier?: number;
  rangeTrait?: string;
}

export interface CharacterItem {
  item: Item;
  itemName: string;
  quantity: number;
  currentWeight: number;
}

export interface PaymentItem {
  gold: number;
  silver: number;
  copper: number;
  itemsExchanged?: CharacterItem[];
  payingParty?: Character;
  receivingParty?: Character;
}