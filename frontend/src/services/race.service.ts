import { useContext, useCallback } from "react";
import { AxiosContext } from "./axios/AxiosContext";
import handleAxiosError from "./axios/axiosErrorHandler";


export const useRaceService = () => {
  const axiosInstance = useContext(AxiosContext);

  const getRaces = useCallback(async () => {
    try {
      const response = await axiosInstance.get('/race');
      return response.data;
    } catch (err) {
      handleAxiosError(err);
    }
  },[axiosInstance]);

  return {
    getRaces
  };
};
