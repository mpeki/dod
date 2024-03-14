import React, { FC, ReactNode, useCallback, useState } from "react";
import { Change, createChange } from "../../types/change";
import CharacterContext from "./CharacterContext";
import { useChangeService } from "../../services/change.service";
import useCharacterService from "../../services/character.service";
import { Character } from "../../types/character";
import { showSuccessSnackbar, showWarningSnackbar } from "../../utils/DODSnackbars";
import { ApiError } from "../../services/axios/axiosErrorHandler";

type CharacterContextProviderProps = {
  children: ReactNode;
};

export const CharacterContextProvider: FC<CharacterContextProviderProps> = ({ children }) => {

  const { doChange } = useChangeService();
  const { getCharacters, getCharacter, deleteCharacter } = useCharacterService();
  const [characters, setCharacters] = useState<Character[]>([]);
  const [currentCharacter, setCurrentCharacter] = useState<Character>();
  const [errorCode, setErrorCode] = useState<number>(0);

  const fetchAllCharsHandler = useCallback(async () => {
    await getCharacters()
    .then(characters => setCharacters(prevChars => characters))
    .catch((e) => {
      if (e instanceof ApiError) {
        console.log(e.messageKey, e.errorCode, e.message);
        setErrorCode(e.errorCode);
        showWarningSnackbar(e.messageKey);
      } else {
        setErrorCode(666);
        showWarningSnackbar("unknown.error");
      }
    });
  }, [getCharacters]);

  const fetchCharHandler = useCallback(async (characterId: string) => {
    try {
      const character = await getCharacter(characterId);
      setCurrentCharacter(character);
    } catch (e) {
      if (e instanceof ApiError) {
        console.log(e.messageKey, e.errorCode, e.message);
        setErrorCode(e.errorCode);
        showWarningSnackbar(e.messageKey);
      } else {
        setErrorCode(666);
        showWarningSnackbar("unknown.error");
      }
    }
  }, [getCharacter]);


  const activateCharHandler = useCallback(async (characterId: string) => {
    const changeData: Change = createChange("CHARACTER_READY_TO_PLAY",
      "Character {character.id} is ready to play",
      "READY_TO_PLAY",
      0);
    if (characterId != null) {
      await doChange(characterId, changeData)
      .then((change: Change) => {
        showSuccessSnackbar("Character activated successfully: " + change.statusLabel);
        fetchAllCharsHandler();
      });
    }
  }, [doChange, fetchAllCharsHandler]);

  const deleteCharHandler = useCallback(async (characterId: string) => {
    if (characterId != null) {
      await deleteCharacter(characterId)
      .then(() => {
        localStorage.removeItem(`${characterId}_startCap`);
        fetchAllCharsHandler();
      })
      .catch((e) => showWarningSnackbar((e as Error).message));
    }
  }, [deleteCharacter, fetchAllCharsHandler]);


  return (
    <CharacterContext.Provider
      value={{ activateCharHandler, fetchCharHandler, fetchAllCharsHandler, deleteCharHandler, currentCharacter, characters, errorCode }}>
      {children}
    </CharacterContext.Provider>
  );
};