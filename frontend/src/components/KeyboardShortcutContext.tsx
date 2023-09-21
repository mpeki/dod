import { createContext, useCallback, useContext, useEffect } from "react";

export type ShortcutDefinition = {
  key: string;
  callback: () => void;
};

type KeyboardShortcutContextType = {
  parentShortcuts?: ShortcutDefinition[];
};
export const KeyboardShortcutContext = createContext<KeyboardShortcutContextType>({});

/**
 * useKeyboardShortcut Hook
 *
 * @param {string[]} keys - Array of keys to listen to.
 * @param {Function} callback - Function to call when key(s) are pressed.
 */
const useKeyboardShortcut = (keys: string[], callback: (event: KeyboardEvent) => void) => {
  const keyPressCallback = useCallback((event: KeyboardEvent) => {
    if (keys.includes(event.key)) {
      callback(event);
    }
  }, [keys, callback]);

  useEffect(() => {
    document.addEventListener('keydown', keyPressCallback);

    return () => {
      document.removeEventListener('keydown', keyPressCallback);
    };
  }, [keyPressCallback]);
};

export default useKeyboardShortcut;