import { createContext, useContext } from 'react';

export type ShortcutDefinition = {
  key: string;
  callback: () => void;
};

type KeyboardShortcutContextType = {
  parentShortcuts?: ShortcutDefinition[];
};
export const KeyboardShortcutContext = createContext<KeyboardShortcutContextType>({});

export const useKeyboardShortcut = () => {
  const context = useContext(KeyboardShortcutContext);
  if (!context) {
    throw new Error("useKeyboardShortcut must be used within a KeyboardShortcutProvider");
  }
  return context;
};
