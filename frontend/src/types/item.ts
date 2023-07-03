export interface Item {
  id?: string;
  itemType: string;
  breakable?: boolean;
  itemKey: string;
  price: number;
  weight: number;
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
}