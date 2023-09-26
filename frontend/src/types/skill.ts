import { Group } from "./group";
import { Category } from "./category";
import { Race } from "./race";
export interface Skill {
  id?: number;
  key: string;
  itemKey?: string;
  price?: number;
  traitName?: string;
  group: Group;
  category: Category;
  baseChance?: string;
  fv?: number;
  experience?: number;
  allowedRaces?: Race[];
  deniedRaces?: Race[];
}