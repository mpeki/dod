import { SnackbarAction, SnackbarKey } from "notistack";
import { Button } from "@mui/material";

export const createReconnectFeedbackSnackbarAction = (): SnackbarAction => {
  const action = (key: SnackbarKey) => (
    <>
      <Button variant="text" onClick={() => window.location.reload()}>reload</Button>
    </>
  );

  return action as SnackbarAction;
};