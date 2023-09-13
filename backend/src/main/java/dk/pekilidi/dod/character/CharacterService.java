package dk.pekilidi.dod.character;

import dk.pekilidi.dod.character.model.BaseTrait;
import dk.pekilidi.dod.character.model.BaseTraitName;
import dk.pekilidi.dod.character.model.DODCharacter;
import dk.pekilidi.dod.data.CharacterDTO;
import dk.pekilidi.dod.data.RaceDTO;
import dk.pekilidi.dod.race.RaceNotFoundException;
import dk.pekilidi.dod.race.RaceRepository;
import dk.pekilidi.dod.race.model.Race;
import dk.pekilidi.dod.rules.DroolsService;
import dk.pekilidi.dod.util.character.CharacterMapper;
import dk.pekilidi.dod.util.repo.OptionalCheck;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import lombok.NonNull;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CharacterService {

  private static final CharacterMapper modelMapper = new CharacterMapper();
  private final CharacterRepository characterRepository;
  private final RaceRepository raceRepository;
  private final DroolsService ruleService;

  public CharacterService(CharacterRepository characterRepository, CharacterRepository characterRepository, RaceRepository raceRepository, DroolsService ruleService) {
    this.characterRepository = characterRepository;
      this.characterRepository = characterRepository;
      this.raceRepository = raceRepository;
      this.ruleService = ruleService;
  }

  @Cacheable("characters")
  public List<DODCharacter> getCharactersByName(String name) {
    List<DODCharacter> chars = characterRepository.findAllByName(name);
    if (chars.isEmpty()) {
      throw new CharacterNotFoundException();
    }
    return chars;
  }

  @CacheEvict(value = "characters", allEntries = true)
  @Transactional
  public CharacterDTO createCharacter(@NonNull CharacterDTO newCharacter) {
    Race race = getRaceByName(newCharacter.getRace().getName());
    newCharacter.setRace(modelMapper.map(race, RaceDTO.class));
    ruleService.executeRulesFor(newCharacter);
    DODCharacter characterEntity = modelMapper.map(newCharacter, DODCharacter.class);
    characterEntity = characterRepository.save(characterEntity);
    newCharacter.setId(characterEntity.getId());
    return newCharacter;
  }

  @CacheEvict(value = "characters", allEntries = true)
  @Transactional
  public void deleteCharacterById(String characterId) {
    characterRepository.deleteById(characterId);
  }

  @Cacheable("characters")
  @Transactional
  public CharacterDTO findCharacterById(String charId) {
    DODCharacter result = OptionalCheck.forDODCharacter(characterRepository.findById(charId));
    return modelMapper.map(result, CharacterDTO.class);
  }

  @CacheEvict(value = "characters", allEntries = true)
  @Transactional
  public CharacterDTO save(CharacterDTO charUpdate) {
    DODCharacter characterEntity = modelMapper.map(charUpdate, DODCharacter.class);
    characterEntity.setBaseTraits(
        modelMapper.map(charUpdate.getBaseTraits(), new TypeToken<Map<BaseTraitName, BaseTrait>>() {}.getType()));
    characterRepository.save(characterEntity);
    return modelMapper.map(characterEntity, CharacterDTO.class);
  }

  @Transactional
  @Cacheable("characters")
  public List<CharacterDTO> fetchAllCharacters() {
    List<DODCharacter> entities = characterRepository.findAll();
    return Arrays.stream(entities.toArray()).map(object -> modelMapper.map(object, CharacterDTO.class)).toList();
  }

  private Race getRaceByName(String name) {
    Race race = raceRepository.findByName(name);
    if (race == null) {
      throw new RaceNotFoundException();
    }
    return race;
  }

  @Transactional
  public List<String> createCharacters(int bulkSize, String raceName) {
    //create a list of bulkSize CharacterDTOs
    List<String> result = new ArrayList<>();
    for (int i = 0; i < bulkSize; i++) {
      result.add(createRandomCharacter(raceName));
    }
    return result;
  }


  private String createRandomCharacter(String raceName){
    Race race = getRaceByName(raceName);

    CharacterDTO newCharacter = new CharacterDTO();
    newCharacter.setRace(modelMapper.map(race, RaceDTO.class));
    newCharacter = createCharacter(newCharacter);

    ruleService.executeRulesFor(newCharacter);

    DODCharacter characterEntity = modelMapper.map(newCharacter, DODCharacter.class);
    characterEntity = characterRepository.save(characterEntity);

    return characterEntity.getId();
  }
}
