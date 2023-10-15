import { closeSnackbar, SnackbarAction, SnackbarKey } from "notistack";
import { IconButton } from "@mui/material";
import CloseIcon from '@mui/icons-material/Close';
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

export const createPrintInfoSnackbarAction = (handleClose: () => void, icon?: React.ReactNode): SnackbarAction => {
  const action = (key: SnackbarKey) => (
    <>
      {icon}
      <IconButton size={"small"} onClick={() => { closeSnackbar(key); handleClose()  }}>
        <CloseIcon style={{ fontSize: '16px' }}/>
      </IconButton>
    </>
  );

  return action as SnackbarAction;
}