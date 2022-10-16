package dk.pekilidi.dod.character;

import dk.pekilidi.dod.character.model.Being;
import org.springframework.data.repository.CrudRepository;

public interface CharacterRepository extends CrudRepository<Being,Long> {
  Being findByName(String name);
}
