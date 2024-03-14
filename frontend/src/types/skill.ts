import { GroupType } from "./group";
import { Category } from "./category";
import { Race } from "./race";
export interface Skill {
  key: string;
  traitName: string;
  category: Category;
  group: GroupType;
  price: number;
  baseChance: string;
  deniedRaces: Race[];
}

export interface CharacterSkill {
  skill: Skill;
  itemKey: string;
  fv: number;
  experience: number;
  skillPointsSpent: number;
  lastUsed: Date;
}

export function createSkill(key: string, group: GroupType, category: Category): Skill;

// Function implementation
export function createSkill(
  key: string,
  group: GroupType,
  category: Category,
): Skill {
  return {
      key: key,
      traitName: 'NONE',
      group: group,
      category: category,
      price: 0,
      baseChance: 'SPECIAL',
      deniedRaces: []
  };
}
