import { Change } from "../types/change";
import { AxiosError } from "axios";
import { useContext, useCallback } from "react";
import { AxiosContext } from "./axios/AxiosContext";

export const useChangeService = () => {

  const axiosInstance = useContext(AxiosContext);

  const doChange = useCallback(async (charId: string, change: Change) => {
    try {
      const response = await axiosInstance.post(`/change/char/${charId}`, change);
      return response.data;
    } catch (err) {
      const axiosError = err as AxiosError;
      throw new Error(`Cannot change character: ${axiosError?.code} ${axiosError?.message}`);
    }
  }, [axiosInstance]);

  return {
    doChange,
  };
};