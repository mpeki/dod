import { BaseTraitValue, Character } from "../types/character";
export interface CharacterHelper {}

export const getValueOfTrait = (character: Character, traitName: string): BaseTraitValue | undefined => {
  const normalizedTraitName = traitName.toUpperCase();
  const baseTraitValue = character.baseTraits?.[normalizedTraitName];
  return baseTraitValue || undefined;
};