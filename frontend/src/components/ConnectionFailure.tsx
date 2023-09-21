import { SnackbarAction, SnackbarKey } from "notistack";
import { IconButton } from "@mui/material";
import ReplayIcon from '@mui/icons-material/Replay';

export const createReconnectFeedbackSnackbarAction = (): SnackbarAction => {
  const action = (key: SnackbarKey) => (
    <>
      <IconButton size="small" style={{ color: 'darkred', background: 'white' }} onClick={() => window.location.reload()}>
        <ReplayIcon />
      </IconButton>
    </>
  );

  return action as SnackbarAction;
};