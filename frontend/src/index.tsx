import React from "react";
import ReactDOM from "react-dom/client";
import { registerConfig } from "./services/config.service";
import "./index.css";
import App from "./App";

const root = ReactDOM.createRoot(
  document.getElementById("root") as HTMLElement
);

registerConfig().then(() => {
  root.render(
    <React.StrictMode>
      <App />
    </React.StrictMode>
  );
});