import axios, { AxiosError } from "axios";
import { Item } from "../types/item";
import { config } from "./config.service";
import { showWarningSnackbar } from "../utils/DODSnackbars";
import { operation, options } from "../utils/DODRetryOptions";
import { createTimeout } from "retry";
import { closeSnackbar, enqueueSnackbar } from "notistack";

export const ItemService = {

  getAllItems: async function (): Promise<Item[]> {
    const fetchItemsUri = `${config.gameApiUri}/items`;

    return new Promise((resolve, reject) => {
      operation.reset();
      operation.attempt(async (currentAttempt) => {
        const currentTimeout = createTimeout(currentAttempt - 1, options);
        try {
          const response = await axios.get(fetchItemsUri);
          if(response.status === 200 && currentAttempt > 1) {
            closeSnackbar();
            enqueueSnackbar("Connection Re-established!", {variant: "success"})
          }
          return resolve(response.data);
        } catch (e) {
          const axiosError = e as AxiosError;
          if (operation.retry(axiosError)) {
            showWarningSnackbar(currentTimeout);
            return;
          }
          reject(
            new Error(`Cannot fetch ITEM data from the game engine: ${axiosError?.message}`)
          );
        }
      });
    });
  },
  getMeleeWeapons: async function (): Promise<Item[]> {
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
