package dk.pekilidi.dod.character;

import dk.pekilidi.dod.character.data.CharacterDTO;
import dk.pekilidi.dod.character.data.RaceDTO;
import dk.pekilidi.dod.character.model.DODCharacter;
import dk.pekilidi.dod.character.model.Race;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;
import org.springframework.transaction.annotation.Transactional;

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
  public DODCharacter getCharacterByName(String name) {
    DODCharacter being = characterRepository.findByName(name);
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

  @Transactional
  public CharacterDTO createCharacter(CharacterDTO newCharacter){
    Race race = getRaceByName(newCharacter.getRace().getName());
    newCharacter.setRace(modelMapper.map(race, RaceDTO.class));
    KieSession kieSession = kieContainer.newKieSession();
    kieSession.insert(newCharacter);
    kieSession.fireAllRules();
    kieSession.dispose();
    DODCharacter characterEntity = modelMapper.map(newCharacter, DODCharacter.class);
    characterEntity.setRace(race);
    characterEntity.setBaseTraits(characterEntity.getBaseTraits());
    characterRepository.save(characterEntity);

     return newCharacter;
  }
}
