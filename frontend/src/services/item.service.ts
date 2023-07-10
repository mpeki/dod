import axios from "axios";
import { Item } from "../types/item";

export const ItemService = {

  getAllItems: async function (): Promise<Item[]> {
    return new Promise((resolve) => {
      const charApiUri = "http://localhost:8090/items";
      setTimeout(() => {
        axios
        .get(charApiUri)
        .then((response) => resolve(response.data))
        .catch((err) => {
          throw new Error(
            `Cannot fetch data from backend: ${err?.code} ${err?.message}`
          );
        });
      }, Math.random() * 100);
    });
  }, getMeleeWeapons: async function (): Promise<Item[]> {
    return new Promise((resolve) => {
      const fetchMeleeWeaponsUri = "http://localhost:8090/items/type/MELEE_WEAPON";
      setTimeout(() => {
        axios
        .get(fetchMeleeWeaponsUri)
        .then((response) => resolve(response.data))
        .catch((err) => {
          throw new Error(
            `Cannot fetch data from backend: ${err?.code} ${err?.message}`
          );
        });
      }, Math.random() * 100);
    });
  },
  createItem: async function (item: Item): Promise<Item> {
    return new Promise((resolve) => {
      const itemApiUri = "http://localhost:8090/item/melee_weapon";
      setTimeout(() => {
        axios.post(itemApiUri, item)
        .then((response) => resolve(response.data))
        .catch((err) => {
          throw new Error(`Cannot fetch data from backend: ${err?.code} ${err?.message}`);
        });
      }, Math.random() * 100);
    });

  },
};
