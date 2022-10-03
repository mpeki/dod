package dk.pekilidi.dod.util.repo;

import static org.junit.jupiter.api.Assertions.assertThrows;

import dk.pekilidi.dod.skill.SkillNotFoundException;
import dk.pekilidi.dod.skill.model.Skill;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.modelmapper.internal.util.Assert;

@Tag("regression")
class OptionalCheckTest {

  @BeforeEach
  void setUp() {

  }

  @Test
  void forDODCharacter() {

  }

  @Test
  void forSkillPresent() {
    Optional<Skill> skillOptional = Optional.of(new Skill());
    Assert.notNull(OptionalCheck.forSkill(skillOptional));
  }

  @Test
  void forSkillNotPresent() {
    Optional<Skill> skillOptional = Optional.ofNullable(null);
    assertThrows(SkillNotFoundException.class, () -> {
      OptionalCheck.forSkill(skillOptional);
    });
  }
}
