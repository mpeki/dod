package dk.dodgame.domain.race;

import dk.dodgame.domain.race.model.Race;
import org.springframework.data.repository.CrudRepository;

public interface RaceRepository extends CrudRepository<Race, Long> {

  Race findByName(String name);
}
