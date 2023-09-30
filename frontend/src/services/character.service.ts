import { useCallback, useContext } from "react";
import { AxiosContext } from "./axios/AxiosContext";
import axiosErrorHandler from "./axios/axiosErrorHandler";
import { Character } from "../types/character";
import { CharacterSkill, Skill } from "../types/skill";
import { GroupType } from "../types/group";

const useCharacterService = () => {

  const axiosInstance = useContext(AxiosContext);

  const getCharacters = useCallback(async () => {
    try {
      const response = await axiosInstance.get("/char");
      return response.data;
    } catch (err) {
      axiosErrorHandler(err);
    }
  }, [axiosInstance]);

  const getCharacter = useCallback(async (charId: string): Promise<Character> => {
    try {
      const response = await axiosInstance.get(`/char/${charId}`);
      Object.keys(response.data.skills).forEach((skillKey) => {
        const charSkill : CharacterSkill = response.data.skills[skillKey];
        charSkill.skill = getCachedSkillForKey(charSkill.skill.key);
      });
      return response.data;
    } catch (err) {
      axiosErrorHandler(err);
    }
  }, [axiosInstance]);

  const getCharactersByName = async (charName: string) => {
    try {
      const response = await axiosInstance.get(`/char/name/${charName}`);
      return response.data;
    } catch (err) {
      axiosErrorHandler(err);
    }
  };

  const deleteCharacter = async (charId: string) => {
    try {
      const response = await axiosInstance.delete(`/char/${charId}`);
      return response.data;
    } catch (err) {
      axiosErrorHandler(err);
    }
  };

  const createCharacter = async (char: any) => {
    try {
      const response = await axiosInstance.post(`/char`, char);
      return response.data;
    } catch (err) {
      axiosErrorHandler(err);
    }
  };

  return {
    getCharacters,
    getCharacter,
    getCharactersByName,
    createCharacter,
    deleteCharacter
  };
};
export default useCharacterService;

function getCachedSkillForKey(key: string): Skill {
  console.log("getCachedSkillForKey " + key);
  let skillJSON = localStorage.getItem("skills");
  if (skillJSON !== null) {
    const skills: Skill[] = JSON.parse(skillJSON);
    if (skills !== undefined) {
      const memSkill: Skill = skills.filter(storedSkill => {
        return storedSkill.key === key;
      })[0];
      if (memSkill !== undefined) {
        memSkill.group = new GroupType(memSkill.group.value);
        return memSkill;
      }
    }
  }
  throw new Error(`Skill with key: ${key} not found in cache`);
}