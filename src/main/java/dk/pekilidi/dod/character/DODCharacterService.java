package dk.pekilidi.dod.character;

import dk.pekilidi.dod.character.data.BeingDTO;
import dk.pekilidi.dod.character.model.Being;
import dk.pekilidi.dod.character.model.Race;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

@Service
public class DODCharacterService {

  @Autowired
  private CharacterRepository characterRepository;

  @Autowired
  private RaceRepository raceRepository;

  @Autowired
  private KieContainer kieContainer;

  private final ModelMapper modelMapper = new ModelMapper();

  public DODCharacterService(CharacterRepository characterRepository) {
      this.characterRepository = characterRepository;
  }

  @Cacheable("characters")
  public Being getCharacterByName(String name) {
    Being being = characterRepository.findByName(name);
    if(being == null){
      throw new CharacterNotFoundException();
    }
    return being;
  }

  @Cacheable("races")
  public Race getRaceByName(String name) {
    Race race = raceRepository.findByName(name);
    if(race == null){
      throw new RaceNotFoundException();
    }
    return race;
  }


  public Being createCharacter(BeingDTO newBeing){
    Race race = getRaceByName(newBeing.getRace().getName());
    Being beingEntity = modelMapper.map(newBeing, Being.class);
    beingEntity.setRace(race);
    KieSession kieSession = kieContainer.newKieSession();
    kieSession.insert(beingEntity);
    kieSession.fireAllRules();
    kieSession.dispose();
    beingEntity = characterRepository.save(beingEntity);

    return beingEntity;
  }
}
