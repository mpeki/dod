package dk.pekilidi.dod.util.repo;

import dk.pekilidi.dod.character.CharacterNotFoundException;
import dk.pekilidi.dod.character.model.DODCharacter;

public class OptionalCheck {

  public static DODCharacter forDODCharacter(java.util.Optional<DODCharacter> optional) {
    if (optional.isPresent()) {
      return optional.get();
    } else {
      throw new CharacterNotFoundException();
    }
  }
}
