package dk.pekilidi.dod.character;

import dk.pekilidi.dod.character.model.Race;
import org.springframework.data.repository.CrudRepository;

public interface RaceRepository extends CrudRepository<Race,Long> {
  Race findByName(String name);
}
