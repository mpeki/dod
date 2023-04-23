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
  id?: string;
  name: string;
  ageGroup: string;
  race: Race;
  looks?: Record<string,string>; //Array<String>;
  favoriteHand?: string;
  socialStatus?: string;
  hero: boolean;
  heroPoints?: number;
  baseTraits?: Map<string,BaseTraitValue>;
  bodyParts?: Map<string,BodyPartValue>;
  baseSkillPoints?: number;
  skills?: Map<string,Skill>
}