import React from 'react';
import { Character } from "../../types/character";

interface CharacterContextType {
  activateCharHandler: (characterId: string) => Promise<void>;
  fetchCharHandler: (characterId: string) => Promise<void>;
  fetchAllCharsHandler: () => Promise<void>;
  deleteCharHandler: (characterId: string) => Promise<void>;
  currentCharacter: Character | undefined;
  characters: Character[];
  errorCode: number;
}

const defaultActivationHandler = async (characterId: string) => {
  throw new Error('activateCharHandler function not initialized');
};

const defaultFetchCharHandler = async (characterId: string) => {
  throw new Error('fetchCharHandler function not initialized');
};

const defaultFetchAllCharsHandler = async () => {
  throw new Error('fetchAllCharsHandler function not initialized');
};

const defaultDeleteCharHandler = async () => {
  throw new Error('deleteCharHandler function not initialized');
};


const CharacterContext = React.createContext<CharacterContextType | undefined>({
  activateCharHandler: defaultActivationHandler,
  fetchCharHandler: defaultFetchCharHandler,
  fetchAllCharsHandler: defaultFetchAllCharsHandler,
  deleteCharHandler: defaultDeleteCharHandler,
  currentCharacter: undefined,
  characters: [],
  errorCode: 0
});

export default CharacterContext;