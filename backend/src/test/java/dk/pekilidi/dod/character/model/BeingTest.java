package dk.pekilidi.dod.character.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dk.pekilidi.dod.character.model.body.HumanoidBody;
import dk.pekilidi.utils.RandomObjectFiller;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("regression")
class BeingTest {

  RandomObjectFiller rof = new RandomObjectFiller();

  @Test
  void testConstructors() throws Exception {
    DODCharacter firstObject = new DODCharacter();
    Long id = firstObject.getId();
    String name = firstObject.getName();
    Race race = firstObject.getRace();

    assertThrows(NullPointerException.class, () -> {
      new DODCharacter(id, name, null, race, null, null, -1, -1, null, false, "", FavoriteHand.RIGHT,
          SocialStatus.DISPOSSESSED, null, null);
    });
    HumanoidBody humanoidBody = new HumanoidBody();
    assertThrows(NullPointerException.class, () -> {
      new DODCharacter("test", null, humanoidBody);
    });
  }

  @Test
  void testRequiredArgsConstructor() {
    DODCharacter newChar = new DODCharacter("Peter", new Race(null, "human", null), new HumanoidBody());
    assertNotNull(newChar);
    assertEquals("Peter", newChar.getName());
  }

  @Test
  void testExpectedNullPointers() throws Exception {
    DODCharacter firstObject = new DODCharacter();
    assertThrows(NullPointerException.class, () -> {
      firstObject.setRace(null);
    });
  }

  @Test
  void testEquals() throws Exception {
    DODCharacter firstObject = rof.createAndFill(DODCharacter.class);
    DODCharacter secondObject = rof.createAndFill(DODCharacter.class);
    assertNotEquals(firstObject, secondObject);
    DODCharacter copied = new DODCharacter(firstObject.getId(), firstObject.getName(), firstObject.getBaseTraits(),
        firstObject.getRace(), firstObject.getAgeGroup(), firstObject.getState(), firstObject.getBaseSkillPoints(),
        firstObject.getHeroPoints(), firstObject.getBody(), false, "", FavoriteHand.RIGHT, SocialStatus.DISPOSSESSED,
        null, null);
    assertEquals(firstObject, copied);
    copied.setName("abe");
    assertNotEquals(firstObject, copied);
  }

  @Test
  void testEqualsNull() throws Exception {
    DODCharacter firstObject = rof.createAndFill(DODCharacter.class);
    assertFalse(firstObject.equals(null)); //NOSONAR
  }

  @Test
  void testEqualsSame() throws Exception {
    DODCharacter firstObject = rof.createAndFill(DODCharacter.class);
    assertEquals(firstObject, firstObject);
  }

  @Test
  void testHashCode() throws Exception {
    DODCharacter firstObject = rof.createAndFill(DODCharacter.class);
    DODCharacter secondObject = rof.createAndFill(DODCharacter.class);
    assertNotEquals(firstObject.hashCode(), secondObject.hashCode());
    DODCharacter copied = new DODCharacter(firstObject.getId(), firstObject.getName(), firstObject.getBaseTraits(),
        firstObject.getRace(), firstObject.getAgeGroup(), firstObject.getState(), firstObject.getBaseSkillPoints(),
        firstObject.getHeroPoints(), firstObject.getBody(), false, "", FavoriteHand.RIGHT, SocialStatus.DISPOSSESSED,
        null, null);
    assertEquals(firstObject.hashCode(), copied.hashCode());
    copied.setName("Heman");
    assertNotEquals(firstObject.hashCode(), copied.hashCode());
  }

  @Test
  void testToString() throws Exception {
    DODCharacter firstObject = rof.createAndFill(DODCharacter.class);
    assertTrue(firstObject.toString().contains("id="));
    assertTrue(firstObject.toString().contains("name="));
  }
}
