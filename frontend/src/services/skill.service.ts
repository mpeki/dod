import { Skill } from "../types/skill";
import { Character } from "../types/character";
import { useCallback, useContext } from "react";
import { AxiosContext } from "./axios/AxiosContext";
import handleAxiosError from "./axios/axiosErrorHandler";

export const useSkillService = () => {

  const axiosInstance = useContext(AxiosContext);
  const trainSkill = async (charId: string, skillKey: string) => {
    try {
      const response = await axiosInstance.post(`/action/training/char/${charId}/skill/${skillKey}`);
      return response.data;
    } catch (err) {
      handleAxiosError(err);
    }
  };
  const getAllSkills = useCallback( async () => {
    try {
      const response = await axiosInstance.get('/skill');
      return response.data;
    } catch (err) {
      handleAxiosError(err);
    }
  },[axiosInstance]);

  return {
    trainSkill,
    getAllSkills,
  }
}

export const SkillUtil = {

  calculateNewSkillPrice: function(
    character: Character,
    skill: Skill | undefined,
    fvToBuy: number
  ): number {
    let freePoints = 0;
    if (
      skill === null ||
      skill === undefined ||
      fvToBuy === null ||
      fvToBuy === undefined
    ) {
      return 0;
    }
    let skillCategory = JSON.stringify(skill.category);
    if (skillCategory === "\"A\"" && character.hero) {
      if (character.baseTraits != null && skill.traitName != null) {
        freePoints = character.baseTraits[skill.traitName].groupValue || 0;
      }
    }
    const pointsToBuy = fvToBuy - freePoints;
    if (pointsToBuy < 1) {
      return 0;
    } else if (skillCategory === "\"B\"" && fvToBuy > 5) {
      throw new Error("Category B skills can't start above 5 fv");
    } else if (skillCategory === "\"A\"" && fvToBuy > 20) {
      throw new Error("Category A skills can't start above 20 fv");
    }
    if (skillCategory === "\"A\"") {
      let result = -1;
      const skillPrice = skill.price || 0;
      const tier1Price = skillPrice * 10;
      const tier2Price = tier1Price + skillPrice * 8;
      const tier3Price = tier2Price + skillPrice * 9;
      switch (pointsToBuy) {
        case 1:
        case 2:
        case 3:
        case 4:
        case 5:
        case 6:
        case 7:
        case 8:
        case 9:
        case 10:
          result = skillPrice * pointsToBuy;
          break;
        case 11:
        case 12:
        case 13:
        case 14:
          result = tier1Price + (pointsToBuy - 10) * skillPrice * 2;
          break;
        case 15:
        case 16:
        case 17:
          result = tier2Price + (pointsToBuy - 14) * skillPrice * 3;
          break;
        case 18:
        case 19:
        case 20:
          result = tier3Price + (pointsToBuy - 17) * skillPrice * 4;
          break;
        default:
          throw new Error("WTF! In calculating A skill price");
      }
      return result;
    } else {
      let result = -1;
      let skillPrice: number = skill.price || 0;
      let tier1Price: number = skillPrice * 2;
      let tier2Price: number = tier1Price + skillPrice * 4;
      switch (pointsToBuy) {
        case 1:
        case 2:
          result = skillPrice * pointsToBuy;
          break;
        case 3:
        case 4:
          result = tier1Price + (pointsToBuy - 2) * skillPrice * 2;
          break;
        case 5:
          result = tier2Price + (pointsToBuy - 4) * skillPrice * 3;
          break;
        default:
          throw new Error("WTF! In calculating B skill price");
      }
      return result;
    }
  }
};
