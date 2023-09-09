import React, { ReactNode, useMemo } from "react";
import { useAuth } from "react-oidc-context";
import { AxiosContext } from "./AxiosContext";
import axios from "axios";
import { config } from "../config.service";


interface AxiosProviderProps {
  children: ReactNode;
}

export const AxiosProvider: React.FC<AxiosProviderProps> = ({ children }) => {
  const auth = useAuth();
  const axiosInstance = useMemo(() => {
    const instance = axios.create({
      baseURL: `${config.gameApiUri}`
    });
    (instance as any)._customRef = Math.random(); // for debugging purposes
    instance.interceptors.request.use(config => {
      if (auth && auth.user && auth.user?.access_token) {
        config.headers = config.headers || {};
        (config.headers as any)["Authorization"] = `Bearer ${auth.user?.access_token}`;
      }
      return config as any;
    }, error => Promise.reject(error));

    return instance;
  }, [auth]);


  return (
    <AxiosContext.Provider value={axiosInstance}>
      {children}
    </AxiosContext.Provider>
  );
};
