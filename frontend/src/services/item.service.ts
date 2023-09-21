import { Item } from "../types/item";
import { useContext } from "react";
import { AxiosContext } from "./axios/AxiosContext";
import handleAxiosError from "./axios/axiosErrorHandler";

export const useItemService = () => {

  const axiosInstance = useContext(AxiosContext);

  const getAllItems = async () => {
    try {
      const response = await axiosInstance.get('/items');
      return response.data;
    } catch (err) {
      handleAxiosError(err);
    }
  };

  const getMeleeWeapons = async () => {
    try {
      const response = await axiosInstance.get('/items/type/MELEE_WEAPON');
      return response.data;
    } catch (err) {
      handleAxiosError(err);
    }
  };

  const createItem = async (item: Item) => {
    try {
      const response = await axiosInstance.post(`/item/melee_weapon`, item);
      return response.data;
    } catch (err) {
      handleAxiosError(err);
    }
  };

  return {
    getAllItems,
    getMeleeWeapons,
    createItem
  };
};