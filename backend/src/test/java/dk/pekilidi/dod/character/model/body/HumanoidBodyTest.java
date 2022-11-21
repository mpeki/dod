package dk.pekilidi.dod.character.model.body;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import dk.pekilidi.dod.character.model.CharacterTemplate;
import dk.pekilidi.dod.race.model.Race;
import dk.pekilidi.utils.RandomObjectFiller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("regression")
class HumanoidBodyTest {

  RandomObjectFiller rof = new RandomObjectFiller();

  @Test
  void testEquals() throws Exception {
    HumanoidBody testObject = rof.createAndFill(HumanoidBody.class);
    testObject.setId(1L);
    HumanoidBody secondObject = rof.createAndFill(HumanoidBody.class);
    secondObject.setId(2L);
    assertNotEquals(testObject, secondObject);
    HumanoidBody copied = testObject.toBuilder().build();
    assertEquals(testObject, copied);
    copied.setId(0L);
    assertNotEquals(testObject, copied);
  }

  @Test
  void testHashCode() throws Exception {
    HumanoidBody firstObject = rof.createAndFill(HumanoidBody.class);
    firstObject.setId(1L);
    HumanoidBody secondObject = rof.createAndFill(HumanoidBody.class);
    secondObject.setId(2L);
    assertNotEquals(firstObject.hashCode(), secondObject.hashCode());
    HumanoidBody copied = firstObject.toBuilder().build();
    assertEquals(firstObject.hashCode(), copied.hashCode());
    copied.setId(0L);
    copied.setChest(BodyPart.builder().currentHP(12).maxHP(12).build());
    assertNotEquals(firstObject.hashCode(), copied.hashCode());
  }
}