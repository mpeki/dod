import axios from "axios";
import { Skill } from "../types/skill";
import { Group } from "../types/group";

export const SkillService = {

  getAllSkills: async function(): Promise<Skill[]> {
    return new Promise((resolve) => {
      const charApiUri = "http://localhost:8090/skill";
      setTimeout(() => {
        axios.get(charApiUri)
        .then((response) => resolve(response.data))
        .catch((err) => {
          throw new Error(`Cannot fetch data from backend: ${err?.code} ${err?.message}`);
        });
      }, Math.random() * 100);
    });
  }
}