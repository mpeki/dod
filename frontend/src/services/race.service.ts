import { AxiosError } from "axios";
import { useContext, useCallback } from "react";
import { AxiosContext } from "./axios/AxiosContext";


export const useRaceService = () => {
  const axiosInstance = useContext(AxiosContext);

  const getRaces = useCallback(async () => {
    try {
      const response = await axiosInstance.get('/race');
      return response.data;
    } catch (err) {
      const axiosError = err as AxiosError;
      throw new Error(`Cannot fetch Race data from the game engine: ${axiosError?.code} ${axiosError?.message}`);
    }
  },[axiosInstance]);

  return {
    getRaces
  };
};
