package dk.dodgame.domain.character.model.body;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.testcontainers.utility.Base58.randomString;

import dk.dodgame.domain.character.model.CharacterTemplate;
import dk.dodgame.util.RandomObjectFiller;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("regression")
class HumanoidBodyTest {

  RandomObjectFiller rof = new RandomObjectFiller();

  @Test
  void testEquals() throws Exception {
    HumanoidBody testObject = rof.createAndFill(HumanoidBody.class);
    testObject.setId(randomString(10));
    HumanoidBody secondObject = rof.createAndFill(HumanoidBody.class);
    secondObject.setId(randomString(10));
    assertNotEquals(testObject, secondObject);
    HumanoidBody copied = testObject.toBuilder().build();
    assertEquals(testObject, copied);
    copied.setId(randomString(10));
    assertNotEquals(testObject, copied);
  }

  @Test
  void testHashCode() throws Exception {
    HumanoidBody firstObject = rof.createAndFill(HumanoidBody.class);
    firstObject.setId(randomString(10));
    HumanoidBody secondObject = rof.createAndFill(HumanoidBody.class);
    secondObject.setId(randomString(10));
    assertNotEquals(firstObject.hashCode(), secondObject.hashCode());
    HumanoidBody copied = firstObject.toBuilder().build();
    assertEquals(firstObject.hashCode(), copied.hashCode());
    copied.setId(randomString(10));
    copied.setChest(BodyPart.builder().currentHP(12).maxHP(12).build());
    assertNotEquals(firstObject.hashCode(), copied.hashCode());
  }

  @Test
  void testEqualsSameObject() throws Exception {
    HumanoidBody testObject = rof.createAndFill(HumanoidBody.class);
    testObject.setId(randomString(10));
    assertEquals(testObject, testObject);
  }

  @Test
  void testEqualsOtherType() throws Exception {
    HumanoidBody testObject = rof.createAndFill(HumanoidBody.class);
    testObject.setId(randomString(10));
    Assertions.assertNotEquals(testObject, new CharacterTemplate());
  }
}