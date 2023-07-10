import { Group } from "./group";
import { Category } from "./category";
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
}