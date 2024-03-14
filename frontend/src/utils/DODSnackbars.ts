import { enqueueSnackbar, closeSnackbar } from "notistack";
import { createCountdownSnackbarAction } from "../components/Countdown";
import { createPrintInfoSnackbarAction, createReconnectFeedbackSnackbarAction } from "../components/ConnectionFailure";

const connectionLostMessages = [
  "Oops! Looks like the game is playing hide and seek.",
  "Connection failed. Time for a facepalm moment!",
  "Unable to connect... This is getting awkward.",
  "Game connection lost. Cue the awkward silence.",
  "Oh no! The game seems to be avoiding us.",
  "Connection failed. Let's hope nobody notices.",
  "Service down? How embarrassing! Let's blame it on the gremlins.",
  "Oops! Unable to establish a connection. Let's try not to blush.",
  "Connection failure. This is cringe-worthy!",
  "Can't connect to the game service. The universe is laughing at us!"
];


export const showNoConnectionWarningSnackbar = (
  currentTimeout: number
): void => {
  closeSnackbar(); // Close any existing snackbars before showing a new one
  enqueueSnackbar(`${connectionLostMessages[Math.floor(Math.random() * 10)]}`, {
    variant: "warning",
    style: {
      display: "flex",
      alignItems: "center",
    },
    preventDuplicate: true,
    autoHideDuration: currentTimeout,
    action: createCountdownSnackbarAction(currentTimeout / 1000)
  });
};

export const showFatalConnectionErrorSnackbar = (message: string, reload: boolean): void => {
  closeSnackbar(); // Close any existing snackbars before showing a new one
  enqueueSnackbar(message, {
    variant: 'error',
    persist: true,
    preventDuplicate: true,
    action: reload ? createReconnectFeedbackSnackbarAction() : undefined
  });
}

export const showWarningSnackbar = (message: string): void => {
  closeSnackbar(); // Close any existing snackbars before showing a new one
  enqueueSnackbar(message, {
    variant: 'warning',
    preventDuplicate: true
  });
}

export const showInfoSnackbar = (message: string): void => {
  closeSnackbar();
  enqueueSnackbar(message, {
    anchorOrigin: {horizontal: 'left', vertical: 'bottom'},
    variant: 'info',
    preventDuplicate: true
  });
}

export const showPrintInfoSnackbar = (message: string, handleClose: () => void, icon?: React.ReactNode ): void => {
  closeSnackbar();
  enqueueSnackbar(message, {
    anchorOrigin: {horizontal: 'left', vertical: 'top'},
    variant: 'info',
    preventDuplicate: true,
    persist: true,
    action: createPrintInfoSnackbarAction(handleClose, icon)
  });
}


export const showSuccessSnackbar = (message: string): void => {
  closeSnackbar();
  enqueueSnackbar(message, {
    variant: 'success',
    preventDuplicate: true
  });
}
