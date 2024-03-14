import React, { Suspense } from "react";
import ReactDOM from "react-dom/client";
import { AuthProvider } from "react-oidc-context";
import { AxiosProvider } from "./services/axios/AxiosProvider";
import { config, registerConfig } from "./services/config.service";
import "./index.css";
import App from "./App";
import { SnackbarProvider } from "notistack";
import "./i18n";
import { AppRouter } from "./components/routing/AppRouter";


function Loading() {
  return <h2>🌀 Loading...</h2>;
}

registerConfig().then(() => {
  const root = ReactDOM.createRoot(
    document.getElementById("root") as HTMLElement
  );

  const oidcConfig = {
    authority: `${config.authority}`,
    client_id: "dodgame-api",
    redirect_uri: `${config.authRedirectUri}`,
    post_logout_redirect_uri: `${config.authRedirectUri}`,
    response_type: "code",
    grant_type: "authorization_code"
  };

  root.render(
    // <React.StrictMode>
    <SnackbarProvider dense maxSnack={5} anchorOrigin={{ vertical: "top", horizontal: "center" }}>
      <Suspense fallback={<Loading />}>
        <AuthProvider {...oidcConfig}>
          <AxiosProvider>
            <AppRouter>
              <App />
            </AppRouter>
          </AxiosProvider>
        </AuthProvider>
      </Suspense>
    </SnackbarProvider>

    // </React.StrictMode>
  );
});