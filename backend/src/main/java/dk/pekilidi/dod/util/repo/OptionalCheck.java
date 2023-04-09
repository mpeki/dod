package dk.pekilidi.dod.util.repo;

import dk.pekilidi.dod.character.CharacterNotFoundException;
import dk.pekilidi.dod.character.model.DODCharacter;
import dk.pekilidi.dod.skill.SkillNotFoundException;
import dk.pekilidi.dod.skill.model.Skill;
import java.util.Optional;

public class OptionalCheck {

  private OptionalCheck() {
  }

  public static DODCharacter forDODCharacter(Optional<DODCharacter> optional) {
    if (optional.isPresent()) {
      return optional.get();
    } else {
      throw new CharacterNotFoundException();
    }
  }

  public static Skill forSkill(Optional<Skill> optional) {
    if (optional.isPresent()) {
      return optional.get();
    } else {
      throw new SkillNotFoundException();
    }
  }
}
