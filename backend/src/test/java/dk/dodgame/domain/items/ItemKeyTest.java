package dk.dodgame.domain.items;

import static org.junit.jupiter.api.Assertions.*;

import dk.dodgame.domain.item.ItemKey;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("regression")
class ItemKeyTest {

  @Test
  void testEquals() {
    ItemKey itemKey = ItemKey.builder().value("item.key.value").build();
    ItemKey itemKey2 = ItemKey.builder().value("item.key.value").build();
    assertEquals(itemKey, itemKey2);
  }

  @Test
  void testEqualsSame() {
    ItemKey itemKey = ItemKey.builder().value("item.key.value").build();
    assertEquals(itemKey, itemKey);
  }


  @Test
  void testOtherObject() {
    ItemKey itemKey = ItemKey.builder().value("item.key.value").build();
    assertNotEquals(itemKey, new Object());
  }


}