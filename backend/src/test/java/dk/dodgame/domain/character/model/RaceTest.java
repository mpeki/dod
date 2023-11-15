package dk.dodgame.domain.character.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.testcontainers.utility.Base58.randomString;

import dk.dodgame.domain.race.model.Race;
import dk.dodgame.domain.skill.SkillKey;
import dk.dodgame.util.RandomObjectFiller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("regression")
class RaceTest {

  RandomObjectFiller rof = new RandomObjectFiller();
  Race firstObject;
  String id;
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
      new Race(id, null, SkillKey.toSkillKey("common"), characterTemplate);
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
    copied.setId(randomString(10));
    assertNotEquals(firstObject, copied);
  }

  @Test
  void testEqualsOtherObject() {
    assertNotEquals(firstObject, new Object());
  }

  @Test
  void testEqualsCloned() throws Exception {
    Race firstObject = rof.createAndFill(Race.class);
    Race secondObject = firstObject.toBuilder().build();
    assertEquals(firstObject, secondObject);
  }

  @Test
  void testEqualsSameObject(){
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
