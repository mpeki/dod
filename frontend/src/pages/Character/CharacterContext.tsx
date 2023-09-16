import React from 'react';
import { Character } from "../../types/character";

interface CharacterContextType {
  activateCharHandler: (characterId: string) => Promise<void>;
  fetchCharsHandler: () => Promise<void>;
  characters: Character[];
}

const defaultActivationHandler = async (characterId: string) => {
  throw new Error('activateCharHandler function not initialized');
};

const defaultFetchHandler = async () => {
  throw new Error('fetchCharHandler function not initialized');
};


const CharacterContext = React.createContext<CharacterContextType | undefined>({
  activateCharHandler: defaultActivationHandler,
  fetchCharsHandler: defaultFetchHandler,
  characters: []
});

export default CharacterContext;