package dk.dodgame.domain.character.model;

import static org.junit.jupiter.api.Assertions.*;

import dk.dodgame.domain.character.model.CharacterItem;
import dk.dodgame.domain.character.model.ItemLocation;
import dk.dodgame.domain.item.ItemKey;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("regression")
class CharacterItemTest {

  CharacterItem item;
  CharacterItem item2;
  @BeforeEach
  void setup() {
    item = CharacterItem
        .builder()
        .itemKey(ItemKey.builder().value("item.key.value").build())
        .id("id1")
        .itemName("itemName")
        .currentHealth(100)
        .quantity(100)
        .location(ItemLocation.CARRIED)
        .build();

    item2 = CharacterItem
        .builder()
        .itemKey(ItemKey.builder().value("item.key.value").build())
        .id("id2")
        .itemName("itemName")
        .currentHealth(100)
        .quantity(100)
        .location(ItemLocation.CARRIED)
        .build();

  }

  @Test
  void getValue() {
    assertEquals("item.key.value", item.getValue());
  }

  @Test
  void testEquals() {
    item2.setTimeCreated(item.getTimeCreated());
    item2.setId(item.getId());
    assertEquals(item, item2);
  }

  @Test
  void testHashCode() {
    item2.setTimeCreated(item.getTimeCreated());
    item2.setId(item.getId());
    assertEquals(item.hashCode(), item2.hashCode());
  }

  @Test
  void testNotEquals() {
    assertNotEquals(item, item2);
  }

  @Test
  void testEqualsOtherObject() {
    assertNotEquals(item, new Object());
  }

  @Test
  void testEqualsSameObject() {
    assertEquals(item, item);
  }

}