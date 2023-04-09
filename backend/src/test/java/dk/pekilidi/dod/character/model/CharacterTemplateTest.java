package dk.pekilidi.dod.character.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import dk.pekilidi.utils.RandomObjectFiller;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("regression")
class CharacterTemplateTest {

  RandomObjectFiller rof = new RandomObjectFiller();

  @Test
  void testEquals() throws Exception {
    CharacterTemplate firstObject = rof.createAndFill(CharacterTemplate.class);
    CharacterTemplate secondObject = rof.createAndFill(CharacterTemplate.class);
    assertNotEquals(firstObject, secondObject);
    CharacterTemplate copied = firstObject.toBuilder().build();
    assertEquals(firstObject, copied);
    copied.setId(0L);
    assertNotEquals(firstObject, copied);
  }

  @Test
  void testEqualsSame() throws Exception {
    CharacterTemplate firstObject = rof.createAndFill(CharacterTemplate.class);
    assertEquals(firstObject, firstObject);
  }

  @Test
  void testEqualsNull() throws Exception {
    CharacterTemplate firstObject = rof.createAndFill(CharacterTemplate.class);
    assertFalse(firstObject.equals(null)); //NOSONAR
  }

  @Test
  void testHashCode() throws Exception {
    CharacterTemplate firstObject = rof.createAndFill(CharacterTemplate.class);
    CharacterTemplate secondObject = rof.createAndFill(CharacterTemplate.class);
    assertNotEquals(firstObject.hashCode(), secondObject.hashCode());
    CharacterTemplate copied = firstObject.toBuilder().build();
    assertEquals(firstObject.hashCode(), copied.hashCode());
    copied.setId(0L);
    assertNotEquals(firstObject.hashCode(), copied.hashCode());
  }
}
