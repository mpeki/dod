// useCharacterItems.ts
import { useEffect, useState } from 'react';
import { Item, CharacterItem } from "../../types/item";
import { Character } from "../../types/character";

function useCharacterItems(currentCharacter: Character | undefined) {
  const [items, setItems] = useState<Map<string, CharacterItem>>(new Map());

  useEffect(() => {
    let itemJSON = localStorage.getItem("items");
    if (itemJSON !== null && currentCharacter && currentCharacter.items) {
      const items: Item[] = JSON.parse(itemJSON);
      const itemsMap = new Map(items.map((item) => [item.itemKey, item]));
      const charItems: Map<string, CharacterItem> = new Map(Object.entries(currentCharacter.items));

      charItems.forEach((charItem, key) => {
        const matchingItem = itemsMap.get(charItem.item.itemKey);
        if (matchingItem) {
          charItem.item = matchingItem;
        }
      });

      setItems(charItems);
    }
  }, [currentCharacter]);

  return items;
}

export default useCharacterItems;
