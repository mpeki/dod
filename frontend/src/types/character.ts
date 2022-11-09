import { Race } from "./race";

export interface BaseTraitValue {
  currentValue: number;
  startValue: number;
  groupValue: number;
}

export interface BodyPartValue {
  maxHP: number;
  currentHP: number;
}

export interface Character {
  id?: string;
  name: string;
  ageGroup: string;
  race: Race;
  hero: boolean;
  baseTraits?: Map<string,BaseTraitValue>;
  bodyParts?: Map<string,BodyPartValue>;
}