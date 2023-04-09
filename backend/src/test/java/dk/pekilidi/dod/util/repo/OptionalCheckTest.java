package dk.pekilidi.dod.util.repo;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import dk.pekilidi.dod.skill.SkillNotFoundException;
import dk.pekilidi.dod.skill.model.Skill;
import dk.pekilidi.dod.util.rules.RulesUtil;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.modelmapper.internal.util.Assert;

@Tag("regression")
class OptionalCheckTest {

  @Test
  void callPrivateConstructorsForCodeCoverage()
      throws SecurityException, NoSuchMethodException, IllegalArgumentException, InstantiationException,
      IllegalAccessException, InvocationTargetException {
    Class<?>[] classesToConstruct = {OptionalCheck.class};
    for (Class<?> clazz : classesToConstruct) {
      Constructor<?> constructor = clazz.getDeclaredConstructor();
      constructor.setAccessible(true);
      Assertions.assertNotNull(constructor.newInstance());
    }
  }


  @Test
  void forSkillPresent() {
    Optional<Skill> skillOptional = Optional.of(new Skill());
    assertNotNull(OptionalCheck.forSkill(skillOptional));
  }

  @Test
  void forSkillNotPresent() {
    Optional<Skill> skillOptional = Optional.ofNullable(null);
    assertThrows(SkillNotFoundException.class, () -> {
      OptionalCheck.forSkill(skillOptional);
    });
  }
}
