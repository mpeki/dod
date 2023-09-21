// useFetchWithRetry.ts
import { useEffect, useState } from "react";
import { useAxios } from "./useAxios";
import { showFatalConnectionErrorSnackbar, showNoConnectionWarningSnackbar } from "../../utils/DODSnackbars";
import { operation, options } from "../../utils/DODRetryOptions";
import { createTimeout } from "retry";
import { closeSnackbar, enqueueSnackbar } from "notistack";
import { AxiosError } from "axios";

export const useLoadAppDataWithRetry = (dataKey: string, endpoint: string ) => {
  const axios = useAxios();
  const [data, setData] = useState<any>(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<AxiosError | null>(null);
  const [currentAttemt, setCurrentAttempt] = useState(0);


  useEffect(() => {
    setLoading(true);
    let dataJSON = localStorage.getItem(dataKey);
    if (endpoint === "/nocall" ){
      return;
    }
    if (dataJSON === null) {
      const fetchData = async () => {
        operation.attempt(async (currentAttempt) => {
          setCurrentAttempt(currentAttempt);
          console.log(`Attempt ${currentAttempt} to fetch ${dataKey} from ${endpoint}`)
          const currentTimeout = createTimeout(currentAttempt - 1, options);

          try {
            const response = await axios.get(endpoint);
            if (response.status === 200 && currentAttempt > 1) {
              closeSnackbar();
              enqueueSnackbar("Connection Re-established!", { variant: "success" });
            }
            setLoading(false);
            setData(response.data);
            localStorage.setItem(dataKey, JSON.stringify(response.data));
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
      };
      fetchData().then(() => {
      }).catch((err) => {
      });
    } else {
      console.log(`${dataKey} already cached.`);
      closeSnackbar();
      setLoading(false);
    }
  }, [dataKey, endpoint]);
  return { loading, error };
};

