import React, { FC, ReactNode, useCallback, useState } from "react";
import { Change, createChange } from "../../types/change";
import CharacterContext from "./CharacterContext";
import { useChangeService } from "../../services/change.service";
import useCharacterService from "../../services/character.service";
import { Character } from "../../types/character";
import { showSuccessSnackbar, showWarningSnackbar } from "../../utils/DODSnackbars";

type CharacterContextProviderProps = {
  children: ReactNode;
};

export const CharacterContextProvider: FC<CharacterContextProviderProps> = ({ children }) => {

  const { doChange } = useChangeService();
  const { getCharacters } = useCharacterService();
  const [characters, setCharacters] = useState<Character[]>([]);

  const fetchCharsHandler = useCallback(async () => {
      await getCharacters()
        .then(characters => setCharacters(prevChars => characters))
        .catch((e) => {
          showWarningSnackbar(e);
        });
  }, [getCharacters]);

  const activateCharHandler = useCallback(async (characterId: string) => {
    const changeData: Change = createChange("CHARACTER_READY_TO_PLAY",
      "Character {character.id} is ready to play",
      "READY_TO_PLAY",
      0);
    console.log("Activating character: " + characterId);
    if (characterId != null) {
      await doChange(characterId, changeData)
      .then((change: Change) => {
        showSuccessSnackbar("Character activated successfully: " + change.statusLabel);
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