import { BaseTraitValue } from "../types/character";
import { Item } from "../types/item";


export const getPriceForSize = (item: Item, charSize: BaseTraitValue = { currentValue: 0, startValue: 0, groupValue: 0 }) => {
  return item.weightReference ? (item.price * ( getWeightForSize(charSize , item.weightReference) || 1)) : item.price
}
export const getWeightForSize = (characterSize: BaseTraitValue, weightRef: string) => {

  const currentCharSize = characterSize.currentValue;

  if (currentCharSize > 5 && currentCharSize < 9) {
    switch (weightRef) {
      case "A":
        return 1.5;
      case "B":
        return 2;
      case "C":
        return 2.5;
      case "D":
        return 3;
      case "E":
        return 4;
      case "F":
        return 4.5;
      case "G":
        return 5.5;
      case "H":
        return 6;
      case "J":
        return 7;
      case "K":
        return 7.5;
    }
  } else if (currentCharSize > 8 && currentCharSize < 13) {
    switch (weightRef) {
      case "A":
        return 2;
      case "B":
        return 2.5;
      case "C":
        return 3;
      case "D":
        return 4;
      case "E":
        return 5;
      case "F":
        return 6;
      case "G":
        return 7;
      case "H":
        return 8;
      case "J":
        return 9;
      case "K":
        return 10;
    }
  } else if (currentCharSize > 12 && currentCharSize < 17) {
    switch (weightRef) {
      case "A":
        return 2.5;
      case "B":
        return 3;
      case "C":
        return 3.5;
      case "D":
        return 5;
      case "E":
        return 6.5;
      case "F":
        return 7.5;
      case "G":
        return 9;
      case "H":
        return 10;
      case "J":
        return 11.5;
      case "K":
        return 12.5;
    }
  }  else if (currentCharSize > 16 && currentCharSize < 21) {
    switch (weightRef) {
      case "A":
        return 3;
      case "B":
        return 3.5;
      case "C":
        return 4.5;
      case "D":
        return 6;
      case "E":
        return 7.5;
      case "F":
        return 9;
      case "G":
        return 10.5;
      case "H":
        return 12;
      case "J":
        return 13.5;
      case "K":
        return 15;
    }
  } else {
    return 200;
  }
};