import { useContext, useCallback } from "react";
import { AxiosContext } from "./axios/AxiosContext";
import axiosErrorHandler from './axios/axiosErrorHandler';

const useCharacterService = () => {

  const axiosInstance = useContext(AxiosContext);

  const getCharacters = useCallback(async () => {
    try {
      const response = await axiosInstance.get('/char');
      return response.data;
    } catch (err) {
      axiosErrorHandler(err)
    }
  },[axiosInstance]);

  const getCharacter = useCallback( async (charId: string) => {
    try {
      const response = await axiosInstance.get(`/char/${charId}`);
      return response.data;
    } catch (err) {
      axiosErrorHandler(err)
    }
  },[axiosInstance]);

  const getCharactersByName = async (charName: string) => {
    try {
      const response = await axiosInstance.get(`/char/name/${charName}`);
      return response.data;
    } catch (err) {
      axiosErrorHandler(err)
    }
  };

  const deleteCharacter = async (charId: string) => {
    try {
      const response = await axiosInstance.delete(`/char/${charId}`);
      return response.data;
    } catch (err) {
      axiosErrorHandler(err)
    }
  };

  const createCharacter = async (char: any) => {
    try {
      const response = await axiosInstance.post(`/char`, char);
      return response.data;
    } catch (err) {
      axiosErrorHandler(err)
    }
  };

  return {
    getCharacters,
    getCharacter,
    getCharactersByName,
    createCharacter,
    deleteCharacter
    // ... other methods
  };
};
export default useCharacterService;