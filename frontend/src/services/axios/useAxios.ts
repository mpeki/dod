import { useContext } from "react";
import { AxiosContext } from "./AxiosContext";

export const useAxios = () => {
  const context = useContext(AxiosContext);
  // console.log((context as any)._customRef); // Log and check if this is the same instance or changing
  return context;
};