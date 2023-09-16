import React, { FC, ReactNode, useCallback, useState } from "react";
import { Change } from "../../types/change";
import CharacterContext from "./CharacterContext";
import { useChangeService } from "../../services/change.service";
import useCharacterService from "../../services/character.service";
import { Character } from "../../types/character";

type CharacterContextProviderProps = {
  children: ReactNode;
};

export const CharacterContextProvider: FC<CharacterContextProviderProps> = ({ children }) => {

  const { doChange } = useChangeService();
  const { getCharacters } = useCharacterService();
  const [characters, setCharacters] = useState<Character[]>([]);

  const fetchCharsHandler = useCallback(async () => {
    try {
      const characters = await getCharacters();
      setCharacters(prevChars => characters)
    } catch (e) {
      const error = e as Error;
      console.log(error.message);
    }
  }, [getCharacters]);

  const activateCharHandler = useCallback(async (characterId: string) => {
    const changeData: Change = {
      changeKey: "READY_TO_PLAY",
      changeType: "CHARACTER_READY_TO_PLAY",
      changeDescription: "Character {character.id} is ready to play",
      modifier: 0
    };
    console.log("Activating character: " + characterId);
    if (characterId != null) {
      await doChange(characterId, changeData)
      .then(() => {
        fetchCharsHandler();
      });
    }
  }, [doChange, fetchCharsHandler]);


  return (
    <CharacterContext.Provider value={{ activateCharHandler, fetchCharsHandler, characters }}>
      {children}
    </CharacterContext.Provider>
  );
};