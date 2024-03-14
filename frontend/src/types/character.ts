import { Race } from "./race";
import { CharacterSkill, Skill } from "./skill";
import { CharacterState } from "./character-state";
import { CharacterItem, Item } from "./item";

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
  id: string;
  name: string;
  ageGroup: string;
  state?: CharacterState;
  race: Race;
  looks?: Record<string,string>; //Array<String>;
  favoriteHand?: string;
  socialStatus?: string;
  hero: boolean;
  heroPoints?: number;
  baseTraits?: Record<string,BaseTraitValue>;
  bodyParts?: Record<string,BodyPartValue>;
  baseSkillPoints?: number;
  skills?: Record<string,CharacterSkill>;
  items?: Record<string,CharacterItem>;
  weightCarried?: number;
}