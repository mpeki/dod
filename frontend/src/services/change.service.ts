import { Change } from "../types/change";
import axios from "axios";


export const ChangeService = {
  doChange: async function(charId: string, change: Change): Promise<Change> {

    return new Promise((resolve) => {

      const changeApiUri = `http://localhost:8090/change/char/${charId}`

      setTimeout(() => {
        axios.post(changeApiUri, change, {
          headers: {
            'Content-Type': 'application/json'
          }})
        .then((response) => resolve(response.data))
        .catch((err) => {
          throw new Error(`Cannot fetch data from backend: ${err?.code} ${err?.message}`);
        });
      }, 0);
    });
  }
};