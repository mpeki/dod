package dk.pekilidi.dod.character;

import dk.pekilidi.dod.character.data.CharacterDTO;
import dk.pekilidi.dod.character.data.RaceDTO;
import dk.pekilidi.dod.character.model.DODCharacter;
import dk.pekilidi.dod.character.model.Race;
import java.util.List;
import java.util.Optional;
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
  public List<DODCharacter> getCharactersByName(String name) {
    List<DODCharacter> chars = characterRepository.findByName(name);
    if(chars == null || chars.isEmpty()){
      throw new CharacterNotFoundException();
    }
    return chars;
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

  @Cacheable("characters")
  public DODCharacter findCharacterById(Long charId){
    Optional<DODCharacter> result = characterRepository.findById(charId);
    if(result.isPresent()){
      return result.get();
    } else {
      throw new CharacterNotFoundException();
    }
  }

//  public void save(CharacterDTO charUpdate) {
//    DODCharacter characterEntity = modelMapper.map(charUpdate, DODCharacter.class);
//    characterRepository.save(characterEntity);
//  }
}
