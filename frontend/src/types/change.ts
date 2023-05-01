import { Character } from "./character";

export interface Change {
  changeType: string;
  changeDescription: string;
  changeKey: string;
  modifier: number;
  objectAfterChange?: Character;
}