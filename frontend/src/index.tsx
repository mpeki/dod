import React, { Suspense } from "react";
import ReactDOM from "react-dom/client";
import { registerConfig } from "./services/config.service";
import "./index.css";
import App from "./App";
import { SnackbarProvider } from "notistack";

function Loading() {
  return <h2>🌀 Loading...</h2>;
}
registerConfig().then(() => {
  const root = ReactDOM.createRoot(
    document.getElementById("root") as HTMLElement
  );
  root.render(
    // <React.StrictMode>
    <SnackbarProvider dense maxSnack={5}  anchorOrigin={{vertical: 'top', horizontal: 'center',}}>
      <Suspense fallback={<Loading />}>
        <App />
      </Suspense>
    </SnackbarProvider>

    // </React.StrictMode>
  );
});