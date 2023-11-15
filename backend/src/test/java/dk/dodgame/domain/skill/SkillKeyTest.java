package dk.dodgame.domain.skill;

import static org.junit.jupiter.api.Assertions.*;

import dk.dodgame.domain.skill.SkillKey;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
@Tag("regression")
class SkillKeyTest {

  @Test
  void testEquals() {
    SkillKey skillKey = SkillKey.builder().value("skill.key.value").build();
    SkillKey skillKey2 = SkillKey.builder().value("skill.key.value").build();
    assertEquals(skillKey, skillKey2);
  }
  @Test
  void testOtherObject() {
    SkillKey skillKey = SkillKey.builder().value("skill.key.value").build();
    assertNotEquals(skillKey, new Object());
  }
}