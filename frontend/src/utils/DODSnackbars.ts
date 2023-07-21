import { enqueueSnackbar, closeSnackbar } from "notistack";
import { createCountdownSnackbarAction } from "../components/Countdown";
import { createReconnectFeedbackSnackbarAction } from "../components/ConnectionFailure";

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


export const showWarningSnackbar = (
  currentTimeout: number
): void => {
  closeSnackbar(); // Close any existing snackbars before showing a new one
  enqueueSnackbar(`${connectionLostMessages[Math.floor(Math.random() * 10)]}`, {
    variant: "warning",
    style: {
      display: "flex",
      alignItems: "center",
    },
    preventDuplicate: false,
    autoHideDuration: currentTimeout,
    action: createCountdownSnackbarAction(currentTimeout / 1000)
  });
};

export const showFatalConnectionErrorSnackbar = (): void => {
  closeSnackbar(); // Close any existing snackbars before showing a new one
  enqueueSnackbar("We tried everything! And it failed, please come back later!", {
    variant: 'error',
    persist: true,
    preventDuplicate: false,
    action: createReconnectFeedbackSnackbarAction()
  });
}

