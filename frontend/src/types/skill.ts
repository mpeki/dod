import { Group } from "./group";
export interface Skill {
  id?: number;
  key: string;
  price: number;
  traitName: string;
  group: Group;
  baseChance: string;
  fv: number;
  experience: number;
}