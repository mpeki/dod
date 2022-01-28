package dk.pekilidi.dod.character;

import dk.pekilidi.dod.character.model.DODCharacter;
import org.springframework.data.repository.CrudRepository;

public interface CharacterRepository extends CrudRepository<DODCharacter,Long> {
  DODCharacter findByName(String name);
}
