import { useCallback, useContext } from "react";
import { AxiosContext } from "./axios/AxiosContext";
import axiosErrorHandler from "./axios/axiosErrorHandler";
import { GameInfo, Health } from "../types/gameInfo";
import axios from "axios";
import { config } from "./config.service";

const useGameInfoService = () => {

  const axiosInstance = useContext(AxiosContext);

  const getGameEngineHealth = useCallback(async () : Promise<Health> => {
    try {
      const response = await axiosInstance.get("/health");
      return response.data;
    } catch (err) {
      axiosErrorHandler(err);
    }
  }, [axiosInstance]);

  const getGameEngineInfo = useCallback(async () : Promise<GameInfo> => {
    try {
      const response = await axiosInstance.get("/info");
      return response.data;
    } catch (err) {
      axiosErrorHandler(err);
    }
  }, [axiosInstance]);

  return {
    getGameEngineHealth,
    getGameEngineInfo,
  };
};
export default useGameInfoService;