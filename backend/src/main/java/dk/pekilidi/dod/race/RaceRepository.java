package dk.pekilidi.dod.race;

import dk.pekilidi.dod.race.model.Race;
import org.springframework.data.repository.CrudRepository;

public interface RaceRepository extends CrudRepository<Race, Long> {

  Race findByName(String name);
}
