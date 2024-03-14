package dk.dodgame.domain.race;

import dk.dodgame.domain.race.model.Race;
import dk.dodgame.data.RaceDTO;
import jakarta.transaction.Transactional;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class RaceService {

  private static final ModelMapper modelMapper = new ModelMapper();
  private final RaceRepository raceRepository;

  public RaceService(RaceRepository raceRepository) {
    this.raceRepository = raceRepository;
  }

  @Cacheable("races")
  public List<RaceDTO> fetchRaces() {
    return modelMapper.map(raceRepository.findAll(), new TypeToken<List<RaceDTO>>() {}.getType());
  }

  @Cacheable("races")
  public RaceDTO getRaceByName(String name) {
    Race race = raceRepository.findByName(name);
    if (race == null) {
      throw new RaceNotFoundException();
    }
    return modelMapper.map(race, RaceDTO.class);
  }
}
