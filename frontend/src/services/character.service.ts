import axios from "axios";
import { Character } from "../types/character";

//Todo: use properties for uri's etc.

export const CharacterService = {

  getCharacters: async function(): Promise<Character[]> {
    return new Promise((resolve) => {
      const charApiUri = "http://localhost:8090/char";
      setTimeout(() => {
        axios.get(charApiUri)
        .then((response) => resolve(response.data))
        .catch((err) => {
          throw new Error(`Cannot fetch data from backend: ${err?.code} ${err?.message}`);
        });
      }, Math.random() * 100);
    });
  },

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
  },

  deleteCharacter: async function(charId: string): Promise<Character> {
    return new Promise((resolve) => {
      const charApiUri = `http://localhost:8090/char/${charId}`;
      setTimeout(() => {
        axios.delete(charApiUri)
        .then((response) => resolve(response.data))
        .catch((err) => {
          throw new Error(`Cannot fetch data from backend: ${err?.code} ${err?.message}`);
        });
      }, Math.random() * 100);
    });
  }


};