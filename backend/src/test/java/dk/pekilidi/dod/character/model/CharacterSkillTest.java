package dk.pekilidi.dod.character.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import dk.pekilidi.dod.skill.SkillKey;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("regression")
class CharacterSkillTest {

  CharacterSkill characterSkill;

  @BeforeEach
  void setup() {
    characterSkill = CharacterSkill
        .builder()
        .skillKey(SkillKey.builder().value("skill.key.value").build())
        .id("id")
        .experience(12)
        .fv(12)
        .build();
  }

  @Test
  void getValue() {
    assertEquals("skill.key.value", characterSkill.getValue());
  }

  @Test
  void testEquals() {
    CharacterSkill characterSkill2 = CharacterSkill
        .builder()
        .skillKey(SkillKey.builder().value("skill.key.value").build())
        .id("id")
        .experience(12)
        .fv(12)
        .build();
    assertEquals(characterSkill, characterSkill2);
  }

  @Test
  void testHashCode() {
    CharacterSkill characterSkill2 = CharacterSkill
        .builder()
        .skillKey(SkillKey.builder().value("skill.key.value").build())
        .id("id")
        .experience(12)
        .fv(12)
        .build();
    assertEquals(characterSkill.hashCode(), characterSkill2.hashCode());
  }

  @Test
  void testNotEquals() {
    CharacterSkill characterSkill2 = CharacterSkill
        .builder()
        .skillKey(SkillKey.builder().value("skill.key.value").build())
        .id("id2")
        .experience(12)
        .fv(12)
        .build();
    assertNotEquals(characterSkill, characterSkill2);
  }

  @Test
  void testEqualsOtherObject() {
    assertNotEquals(characterSkill, new Object());
  }

  @Test
  void testEqualsSameObject() {
    assertEquals(characterSkill, characterSkill);
  }
}