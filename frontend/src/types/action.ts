import { Character } from "./character";

export interface Action {
  actionResult: string;
  actionDescription: string;
  actor: Character;
  difficulty: number;
  skillKey: string;
  type: string;
}