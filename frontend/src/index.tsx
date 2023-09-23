import React, { Suspense } from "react";
import ReactDOM from "react-dom/client";
import { AuthProvider } from "react-oidc-context";
import { AxiosProvider } from "./services/axios/AxiosProvider";
import { config, registerConfig } from "./services/config.service";
import "./index.css";
import App from "./App";
import { SnackbarProvider } from "notistack";
import "./i18n";
import i18n from "i18next";
import { I18nextProvider } from "react-i18next";


function Loading() {
  return <h2>🌀 Loading...</h2>;
}

registerConfig().then(() => {
  const root = ReactDOM.createRoot(
    document.getElementById("root") as HTMLElement
  );

  const oidcConfig = {
    authority: "http://security:8181/realms/dodgame",
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
        <I18nextProvider i18n={i18n}>
          <AuthProvider {...oidcConfig}>
            <AxiosProvider>
              <App />
            </AxiosProvider>
          </AuthProvider>
        </I18nextProvider>
      </Suspense>
    </SnackbarProvider>

    // </React.StrictMode>
  );
});