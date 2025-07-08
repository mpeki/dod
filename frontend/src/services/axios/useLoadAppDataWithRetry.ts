import { useEffect, useState } from "react";
import { useAxios } from "./useAxios";
import { showFatalConnectionErrorSnackbar, showNoConnectionWarningSnackbar } from "../../utils/DODSnackbars";
import { operation, options } from "../../utils/DODRetryOptions";
import { createTimeout, CreateTimeoutOptions } from "retry";
import { closeSnackbar, enqueueSnackbar } from "notistack";
import { AxiosError } from "axios";


interface AppDataEndpoint {
  [key: string]: string;
}

export const useLoadAppDataWithRetry = (appDataEndpoints: AppDataEndpoint[]) => {

  const axios = useAxios();
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<AxiosError | null>(null);

  useEffect(() => {
    setLoading(true);

    const fetchDataSequentially = async () => {
      for (const endpoint of appDataEndpoints) {
        const dataKey = Object.keys(endpoint)[0];
        const url = endpoint[dataKey];
        let dataJSON = localStorage.getItem(dataKey);

        if (dataJSON === null) {
          try {
            operation.attempt(async (currentAttempt) => {
              const currentTimeout = createTimeout(
                currentAttempt - 1,
                options as CreateTimeoutOptions
              );

              try {
                for (const key in endpoint) {
                  const url = endpoint[key];
                  if(url){
                    const response = await axios.get(url);
                    if (response.status === 200 && currentAttempt > 1) {
                      closeSnackbar();
                      enqueueSnackbar("Connection Re-established!", { variant: "success" });
                    }
                    setLoading(false);
                    localStorage.setItem(dataKey, JSON.stringify(response.data));
                  }
                }
              } catch (e) {
                const axiosError = e as AxiosError;
                setError(axiosError);
                if (operation.retry(axiosError)) {
                  showNoConnectionWarningSnackbar(currentTimeout);
                  return;
                } else {
                  showFatalConnectionErrorSnackbar("We tried everything! And it failed, please come back later or press RELOAD and try again!", true);
                  operation.reset();
                  setLoading(false);
                }
              }
            });
            const response = await axios.get(url);
            // Handle successful response
            localStorage.setItem(dataKey, JSON.stringify(response.data));
            setLoading(false);
          } catch (e) {
            const axiosError = e as AxiosError;
            setError(axiosError);
            // Exit the loop as an error occurred
            break;
          }
        } else {
          console.log(`${dataKey} already cached.`);
        }
      }

      setLoading(false);
    };

    fetchDataSequentially().then(() => {}).catch((err) => {});

  }, []); // eslint-disable-line react-hooks/exhaustive-deps
  //Only want this to run once on mount
  return { loading, error };
};

