import { useEffect, useState } from "react";
import { SnackbarAction, SnackbarKey } from "notistack";

interface IProps {
  message : string;
  startTime : number;
}

export const Countdown = ({ message, startTime } : IProps ) : React.JSX.Element => {
  const [counter, setCounter] = useState(startTime);
  useEffect(() => {
    counter > 0 && setTimeout(() => setCounter(counter - 1), 1000);
  }, [counter]);
  return (
    <>{message} {counter}s</>
  )
}

export const createCountdownSnackbarAction = ( startTime: number): SnackbarAction => {
  const action = (key: SnackbarKey) => (
    <Countdown message="Retrying in" startTime={Math.floor(startTime)} />
  );

  return action as SnackbarAction;
};