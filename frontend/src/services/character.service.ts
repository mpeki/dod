import { AxiosError } from "axios";
import { useContext, useCallback } from "react";
import { AxiosContext } from "./axios/AxiosContext";

const useCharacterService = () => {

  const axiosInstance = useContext(AxiosContext);

  const getCharacters = useCallback(async () => {
    try {
      const response = await axiosInstance.get('/char');
      return response.data;
    } catch (err) {
      const axiosError = err as AxiosError;
      throw new Error(`Cannot fetch character data from the game engine: ${axiosError?.code} ${axiosError?.message}`);
    }
  },[axiosInstance]);

  const getCharacter = useCallback( async (charId: string) => {
    try {
      const response = await axiosInstance.get(`/char/${charId}`);
      return response.data;
    } catch (err) {
      const axiosError = err as AxiosError;
      throw new Error(`Cannot fetch character data from the game engine: ${axiosError?.code} ${axiosError?.message}`);
    }
  },[axiosInstance]);

  const getCharactersByName = async (charName: string) => {
    try {
      const response = await axiosInstance.get(`/char/name/${charName}`);
      return response.data;
    } catch (err) {
      const axiosError = err as AxiosError;
      throw new Error(`Cannot fetch character data from the game engine: ${axiosError?.code} ${axiosError?.message}`);
    }
  };

  const deleteCharacter = async (charId: string) => {
    try {
      const response = await axiosInstance.delete(`/char/${charId}`);
      return response.data;
    } catch (err) {
      const axiosError = err as AxiosError;
      throw new Error(`Cannot delete character: ${axiosError?.code} ${axiosError?.message}`);
    }
  };

  const createCharacter = async (char: any) => {
    try {
      const response = await axiosInstance.post(`/char`, char);
      return response.data;
    } catch (err) {
      const axiosError = err as AxiosError;
      throw new Error(`Cannot create character: ${axiosError?.code} ${axiosError?.message}`);
    }
  };

  return {
    getCharacters,
    getCharacter,
    getCharactersByName,
    createCharacter,
    deleteCharacter
    // ... other methods
  };
};

export default useCharacterService;


/*export const CharacterService = {

  getCharacter: async function(charId: string): Promise<Character> {
    return new Promise((resolve) => {
      const charApiUri = `http://localhost:8090/char/${charId}`;
      setTimeout(() => {
        axios.get(charApiUri)
        .then((response) => resolve(response.data))
        .catch((err) => {
          throw new Error(`Cannot fetch data from backend: ${err?.code} ${err?.message}`);
        });
      }, Math.random() * 100);
    });
  },

  getCharactersByName: async function(charName: string): Promise<Character[]> {
    return new Promise((resolve) => {
      const charApiUri = `http://localhost:8090/char/name/${charName}`;
      setTimeout(() => {
        axios.get(charApiUri)
        .then((response) => resolve(response.data))
        .catch((err) => {
          throw new Error(`Cannot fetch data from backend: ${err?.code} ${err?.message}`);
        });
      }, Math.random() * 100);
    });
  },

  createCharacter: async function(char: Character): Promise<Character> {
    return new Promise((resolve) => {
      const charApiUri = "http://localhost:8090/char";
      setTimeout(() => {
        axios.post(charApiUri, char)
        .then((response) => resolve(response.data))
        .catch((err) => {
          throw new Error(`Cannot fetch data from backend: ${err?.code} ${err?.message}`);
        });
      }, Math.random() * 100);
    });
  }
}; */