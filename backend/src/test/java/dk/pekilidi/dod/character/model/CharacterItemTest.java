package dk.pekilidi.dod.character.model;

import static org.junit.jupiter.api.Assertions.*;

import dk.pekilidi.dod.items.ItemKey;
import dk.pekilidi.dod.skill.SkillKey;
import java.time.LocalDateTime;
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
        .id("id")
        .itemName("itemName")
        .currentHealth(100)
        .quantity(100)
        .location(ItemLocation.CARRIED)
        .build();
    item2 = CharacterItem
        .builder()
        .itemKey(ItemKey.builder().value("item.key.value").build())
        .id("id")
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
    assertEquals(item, item2);
  }

  @Test
  void testHashCode() {
    item2.setTimeCreated(item.getTimeCreated());
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