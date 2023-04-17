import { Change } from "../types/change";
import axios from "axios";


export const ChangeService = {
  buySkill: async function(charId: number, change: Change): Promise<Change> {

    return new Promise((resolve) => {

      const changeApiUri = `http://localhost:8090/change/char/${charId}`

      setTimeout(() => {
        axios.post(changeApiUri, change)
        .then((response) => resolve(response.data))
        .catch((err) => {
          throw new Error(`Cannot fetch data from backend: ${err?.code} ${err?.message}`);
        });
      }, Math.random() * 100);
    });
  }
};