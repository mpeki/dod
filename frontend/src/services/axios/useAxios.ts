import { useContext } from "react";
import { AxiosContext } from "./AxiosContext";

export const useAxios = () => {
  return useContext(AxiosContext);
};