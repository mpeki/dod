import { ReactNode, useContext, useMemo, useState } from "react";
import { useKeyPress } from "./useKeyPress";
import { KeyboardShortcutContext, ShortcutDefinition } from "./KeyboardShortcutContext";
import { useAuth } from "react-oidc-context";


type KeyboardShortcutProviderProps = {
  children: ReactNode;
  shortcuts: ShortcutDefinition[];
};


export const KeyboardShortcutProvider: React.FC<KeyboardShortcutProviderProps> = ({ children, shortcuts }) => {
  const auth = useAuth();

  // Get parent shortcuts from context
  const parentShortcutsContext = useContext(KeyboardShortcutContext);

  // Merge the parent shortcuts and the current shortcuts
  const allShortcuts = useMemo(() => {
    const parentShortcuts = parentShortcutsContext.parentShortcuts || [];

    // Create a map from parentShortcuts
    const mergedShortcuts = new Map(
      parentShortcuts.map(s => [s.key, s])
    );

    // Overwrite or add new shortcuts from current shortcuts
    shortcuts.forEach(s => {
      mergedShortcuts.set(s.key, s);
    });

    return Array.from(mergedShortcuts.values());
  }, [parentShortcutsContext.parentShortcuts, shortcuts]);

  // Create a map of key combinations to callbacks
  const initialShortcuts = useMemo(() => {
    const map = new Map<string, () => void>();
    allShortcuts.forEach(s => map.set(s.key, s.callback));
    return map;
  }, [allShortcuts]);

  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  const [registeredShortcuts, setRegisteredShortcuts] = useState<Map<string, () => void>>(initialShortcuts);
  useKeyPress(Array.from(registeredShortcuts.keys()), (event) => {
    const targetElement = event.target as HTMLElement;
    // Check if the target element of the keypress event is an input, textarea, or select
    if (
      targetElement.tagName === "INPUT" ||
      targetElement.tagName === "TEXTAREA" ||
      targetElement.tagName === "SELECT"
    ) {
      event.key === "Escape" && targetElement.blur();
      return; // If it is, do not proceed with executing the keyboard shortcut
    }
    // Generate a string to represent the key combination
    let keyCombination = "";
    if (event.shiftKey) keyCombination += "Shift+";
    if (event.altKey) keyCombination += "Alt+";
    // ... similarly, you can handle other modifiers like Ctrl, Alt, etc.
    keyCombination += event.key;
    if (auth.isAuthenticated) {
      const callback = registeredShortcuts.get(keyCombination);
      callback && callback();
      event.stopPropagation(); // Stop the event from propagating further
      event.preventDefault(); // Prevent any default browser behavior
    }
  });

  return <KeyboardShortcutContext.Provider
    value={{ parentShortcuts: allShortcuts }}>{children}</KeyboardShortcutContext.Provider>;
};