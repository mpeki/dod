import { createContext, useContext } from 'react';

type KeyboardShortcutContextType = {
  registerShortcut?: (keys: string[], callback: () => void) => void;
  unregisterShortcut?: (keys: string[]) => void;
};

export const KeyboardShortcutContext = createContext<KeyboardShortcutContextType>({});

export const useKeyboardShortcut = () => {
  const context = useContext(KeyboardShortcutContext);
  if (!context) {
    throw new Error("useKeyboardShortcut must be used within a KeyboardShortcutProvider");
  }
  return context;
};
