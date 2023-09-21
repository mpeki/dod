import { useCallback, useEffect, useLayoutEffect, useRef } from 'react';

export const useKeyPress = (keys: string[], callback: (event: KeyboardEvent) => void, node: HTMLElement | null = null) => {
  // implement the callback ref pattern
  const callbackRef = useRef<(event: KeyboardEvent) => void>(callback);
  useLayoutEffect(() => {
    callbackRef.current = callback;
  });

  const keySet = useRef(new Set(keys));
  // handle what happens on key press
  const handleKeyPress = useCallback(
    (event: KeyboardEvent) => {
      if (keySet.current.has(event.key)) {
        callbackRef.current(event);
      }
    },
    []
  );

  useEffect(() => {
    // target is either the provided node or the document
    const targetNode: Document | (EventTarget & HTMLElement) | null = node ?? document;

    // attach the event listener
    if (targetNode) {
      targetNode.addEventListener("keydown", handleKeyPress as EventListener);
    }

    // remove the event listener
    return () =>
      targetNode &&
      targetNode.removeEventListener("keydown", handleKeyPress as EventListener);
  }, [handleKeyPress, node]);
};