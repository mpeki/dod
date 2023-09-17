import { ReactNode, useMemo, useState } from "react";
import { useKeyPress } from "./useKeyPress";
import { KeyboardShortcutContext } from "./KeyboardShortcutContext";
import { useAuth } from "react-oidc-context";

type ShortcutDefinition = {
  key: string;
  callback: () => void;
};

type KeyboardShortcutProviderProps = {
  children: ReactNode;
  shortcuts: ShortcutDefinition[];
};

export const KeyboardShortcutProvider: React.FC<KeyboardShortcutProviderProps> = ({ children, shortcuts }) => {
  const auth = useAuth();
  const initialShortcuts = useMemo(() => {
    const map = new Map<string, () => void>();
    shortcuts.forEach(s => map.set(s.key, s.callback));
    return map;
  }, [shortcuts]);

  const [registeredShortcuts, setRegisteredShortcuts] = useState<Map<string, () => void>>(initialShortcuts);

  useKeyPress(Array.from(registeredShortcuts.keys()), (event) => {
    const targetElement = event.target as HTMLElement;

    // Check if the target element of the keypress event is an input, textarea, or select
    if (
      targetElement.tagName === "INPUT" ||
      targetElement.tagName === "TEXTAREA" ||
      targetElement.tagName === "SELECT"
    ) {
      return; // If it is, do not proceed with executing the keyboard shortcut
    }
    if (auth.isAuthenticated) {
      const callback = registeredShortcuts.get(event.key);
      callback && callback();
      event.stopPropagation(); // Stop the event from propagating further
      event.preventDefault(); // Prevent any default browser behavior
    }
  });

  return <KeyboardShortcutContext.Provider value={{}}>{children}</KeyboardShortcutContext.Provider>;
};

/*
export const KeyboardShortcutProvider: React.FC<KeyboardShortcutProviderProps> = ({ children }) => {
  const [shortcuts, setShortcuts] = useState<Map<string, () => void>>(new Map());
  const shortcutKeys = useMemo(() => Array.from(shortcuts.keys()), [shortcuts]);

  const registerShortcut = useCallback((keys: string[], callback: () => void) => {
    setShortcuts(prevShortcuts => {
      const newShortcuts = new Map(prevShortcuts);
      keys.forEach(key => newShortcuts.set(key, callback));
      return newShortcuts;
    });
  }, []);

  const unregisterShortcut = useCallback((keys: string[]) => {
    setShortcuts(prevShortcuts => {
      const newShortcuts = new Map(prevShortcuts);
      keys.forEach(key => newShortcuts.delete(key));
      return newShortcuts;
    });
  }, []);

  useKeyPress(shortcutKeys, (event) => {
    const callback = shortcuts.get(event.key);
    callback && callback();
  });

  return (
    <KeyboardShortcutContext.Provider value={{ registerShortcut, unregisterShortcut }}>
      {children}
    </KeyboardShortcutContext.Provider>
  );
};
*/
