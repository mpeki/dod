import { Change } from "../types/change";
import { useContext, useCallback } from "react";
import { AxiosContext } from "./axios/AxiosContext";
import handleAxiosError from "./axios/axiosErrorHandler";
import { Character } from "../types/character";

export const useChangeService = () => {

  const axiosInstance = useContext(AxiosContext);

  const doCharacterChange = useCallback(async (character: Character, change: Change) => {
    try {
      const charId = character.id;
      const response = await axiosInstance.post(`/change/char/${charId}`, change);
      return response.data;
    } catch (err) {
      handleAxiosError(err)
    }
  }, [axiosInstance]);


  const doChange = useCallback(async (charId: string, change: Change) => {
    try {
      const response = await axiosInstance.post(`/change/char/${charId}`, change);
      return response.data;
    } catch (err) {
      handleAxiosError(err)
    }
  }, [axiosInstance]);

  return {
    doChange,
    doCharacterChange
  };
};