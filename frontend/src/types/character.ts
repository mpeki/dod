import { Race } from "./race";
import { Skill } from "./skill";

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
  id?: number;
  name: string;
  ageGroup: string;
  race: Race;
  hero: boolean;
  baseTraits?: Map<string,BaseTraitValue>;
  bodyParts?: Map<string,BodyPartValue>;
  skills?: Map<string,Skill>
}