import { Change } from "../types/change";
import { useContext, useCallback } from "react";
import { AxiosContext } from "./axios/AxiosContext";
import handleAxiosError from "./axios/axiosErrorHandler";

export const useChangeService = () => {

  const axiosInstance = useContext(AxiosContext);

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
  };
};