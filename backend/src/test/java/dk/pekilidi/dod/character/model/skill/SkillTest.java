package dk.pekilidi.dod.character.model.skill;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dk.pekilidi.dod.skill.SkillKey;
import dk.pekilidi.dod.skill.model.Skill;
import dk.pekilidi.utils.RandomObjectFiller;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("regression")
class SkillTest {

  static private RandomObjectFiller rof = new RandomObjectFiller();

  @Test
  void testEquals() throws Exception {
    Skill firstObject = rof.createAndFill(Skill.class);
    Skill secondObject = rof.createAndFill(Skill.class);
    assertNotEquals(firstObject, secondObject);
    Skill copied = new Skill(firstObject.getId(), firstObject.getKey(), firstObject.getTraitName(),
        firstObject.getCategory(), firstObject.getGroup(), firstObject.getPrice(), firstObject.getBaseChance());
    assertEquals(firstObject, copied);
    copied.setKey(SkillKey.builder().value("abe").build());
    assertNotEquals(firstObject, copied);
  }

  @Test
  void testEqualsNull() throws Exception {
    Skill firstObject = rof.createAndFill(Skill.class);
    assertFalse(firstObject.equals(null)); //NOSONAR
  }

  @Test
  void testEqualsSame() throws Exception {
    Skill firstObject = rof.createAndFill(Skill.class);
    assertEquals(firstObject, firstObject);
  }

  @Test
  void testHashCode() throws Exception {
    Skill firstObject = rof.createAndFill(Skill.class);
    Skill secondObject = rof.createAndFill(Skill.class);
    assertNotEquals(firstObject.hashCode(), secondObject.hashCode());
    Skill copied = new Skill(firstObject.getId(), firstObject.getKey(), firstObject.getTraitName(),
        firstObject.getCategory(), firstObject.getGroup(), firstObject.getPrice(), firstObject.getBaseChance());
    copied.setKey(SkillKey.builder().value("Heman").build());
    assertNotEquals(firstObject.hashCode(), copied.hashCode());
  }

  @Test
  void testToString() throws Exception {
    Skill firstObject = rof.createAndFill(Skill.class);
    assertTrue(firstObject.toString().contains("id="));
    assertTrue(firstObject.toString().contains("name="));
  }
}
