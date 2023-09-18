import { MemoryRouter, useNavigate } from "react-router-dom";
import { StaticRouter } from "react-router-dom/server";
import { KeyboardShortcutProvider } from "../KeyboardShortcutProvider";
import { ReactNode } from "react";

const GlobalShortcuts = ({ children }: { children?: ReactNode }) => {
  const navigate = useNavigate();
  const globalShortcuts = [
    { key: "1", callback: () => navigate("/home") },
    { key: "2", callback: () => navigate("/characters") },
    { key: "3", callback: () => navigate("/city") },
    { key: "4", callback: () => navigate("/wilderness") },
    { key: "5", callback: () => navigate("/settings") }
  ];

  return (
    <KeyboardShortcutProvider shortcuts={globalShortcuts}>
      {children}
    </KeyboardShortcutProvider>
  );
};

export const AppRouter = (props: { children?: ReactNode }) => {
  const { children } = props;
  if (typeof window === "undefined") {
    return <StaticRouter location="/home">{children}</StaticRouter>;

  }

  return (
    <MemoryRouter initialEntries={["/home"]} initialIndex={0}>
      <GlobalShortcuts>
        {children}
      </GlobalShortcuts>
    </MemoryRouter>
  );

};
