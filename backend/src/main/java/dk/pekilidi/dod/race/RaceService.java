package dk.pekilidi.dod.race;

import dk.pekilidi.dod.data.RaceDTO;
import dk.pekilidi.dod.race.model.Race;
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
  @Transactional
  public List<RaceDTO> fetchRaces() {
    return modelMapper.map(raceRepository.findAll(), new TypeToken<List<RaceDTO>>() {}.getType());
  }

  @Cacheable("races")
  @Transactional
  public RaceDTO getRaceByName(String name) {
    Race race = raceRepository.findByName(name);
    if (race == null) {
      throw new RaceNotFoundException();
    }
    return modelMapper.map(race, RaceDTO.class);
  }
}
