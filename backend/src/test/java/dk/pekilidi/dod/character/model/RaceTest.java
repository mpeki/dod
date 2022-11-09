package dk.pekilidi.dod.character.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import dk.pekilidi.dod.race.model.Race;
import dk.pekilidi.utils.RandomObjectFiller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("regression")
class RaceTest {

  RandomObjectFiller rof = new RandomObjectFiller();
  Race firstObject;
  Long id;
  String name;
  CharacterTemplate characterTemplate;

  @BeforeEach
  void setup() {
    firstObject = new Race();
    id = firstObject.getId();
    name = firstObject.getName();
    characterTemplate = firstObject.getCharacterTemplate();
  }

  @Test
  void testConstructors() throws Exception {

    assertThrows(NullPointerException.class, () -> {
      new Race(id, null, characterTemplate);
    });
  }

  @Test
  void testExpectedNullPointers() {
    assertThrows(NullPointerException.class, () -> {
      firstObject.setName(null);
    });
  }

  @Test
  void testEquals() throws Exception {
    Race firstObject = rof.createAndFill(Race.class);
    Race secondObject = rof.createAndFill(Race.class);
    assertNotEquals(firstObject, secondObject);
    Race copied = firstObject.toBuilder().build();
    assertEquals(firstObject, copied);
    copied.setId(0L);
    assertNotEquals(firstObject, copied);
  }

  @Test
  void testEqualsSame() throws Exception {
    Race firstObject = rof.createAndFill(Race.class);
    assertEquals(firstObject, firstObject);
  }

  @Test
  void testEqualsNull() throws Exception {
    Race firstObject = rof.createAndFill(Race.class);
    assertNotEquals(null, firstObject);
  }

  @Test
  void testHashCode() throws Exception {
    Race firstObject = rof.createAndFill(Race.class);
    Race secondObject = rof.createAndFill(Race.class);
    assertNotEquals(firstObject.hashCode(), secondObject.hashCode());
    Race copied = firstObject.toBuilder().build();
    assertEquals(firstObject.hashCode(), copied.hashCode());
    copied.setName("Heman");
    assertNotEquals(firstObject.hashCode(), copied.hashCode());
  }
}
