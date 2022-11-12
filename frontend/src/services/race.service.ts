import axios from "axios";
import { Race } from "../types/race";

export const RaceService = {

  getRaces: async function(): Promise<Race[]> {
    return new Promise((resolve) => {
      const charApiUri = "http://localhost:8090/race";
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