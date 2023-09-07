import { AxiosError } from "axios";
import { Item } from "../types/item";
import { useContext } from "react";
import { AxiosContext } from "./axios/AxiosContext";

export const useItemService = () => {

  const axiosInstance = useContext(AxiosContext);

  const getAllItems = async () => {
    try {
      const response = await axiosInstance.get('/items');
      return response.data;
    } catch (err) {
      const axiosError = err as AxiosError;
      throw new Error(`Cannot fetch ITEM data from the game engine: ${axiosError?.code} ${axiosError?.message}`);
    }
  };

  const getMeleeWeapons = async () => {
    try {
      const response = await axiosInstance.get('/items/type/MELEE_WEAPON');
      return response.data;
    } catch (err) {
      const axiosError = err as AxiosError;
      throw new Error(`Cannot fetch ITEM data from the game engine: ${axiosError?.code} ${axiosError?.message}`);
    }
  };

  const createItem = async (item: Item) => {
    try {
      const response = await axiosInstance.post(`/item/melee_weapon`, item);
      return response.data;
    } catch (err) {
      const axiosError = err as AxiosError;
      throw new Error(`Cannot create ITEM: ${axiosError?.code} ${axiosError?.message}`);
    }
  };

  return {
    getAllItems,
    getMeleeWeapons,
    createItem
  };
};