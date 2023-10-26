package dk.pekilidi.dod.character;

import dk.pekilidi.dod.character.model.BaseTrait;
import dk.pekilidi.dod.character.model.BaseTraitName;
import dk.pekilidi.dod.character.model.DODCharacter;
import dk.pekilidi.dod.config.ConfigurationService;
import dk.pekilidi.dod.data.CharacterDTO;
import dk.pekilidi.dod.data.RaceDTO;
import dk.pekilidi.dod.race.RaceNotFoundException;
import dk.pekilidi.dod.race.RaceRepository;
import dk.pekilidi.dod.race.model.Race;
import dk.pekilidi.dod.rules.DroolsService;
import dk.pekilidi.dod.util.character.CharacterMapper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import lombok.NonNull;
import org.modelmapper.TypeToken;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CharacterService {

  private static final CharacterMapper modelMapper = new CharacterMapper();
  private final CharacterRepository characterRepository;
  private final RaceRepository raceRepository;
  private final DroolsService ruleService;
  private final ConfigurationService configurationService;

  public CharacterService(CharacterRepository characterRepository, RaceRepository raceRepository,
      DroolsService ruleService, ConfigurationService configurationService) {
    this.characterRepository = characterRepository;
    this.raceRepository = raceRepository;
    this.ruleService = ruleService;
    this.configurationService = configurationService;
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
  @Transactional(propagation = Propagation.NESTED)
  public CharacterDTO createCharacter(@NonNull CharacterDTO newCharacter,@NonNull String owner) {
    if(isCharacterLimitReached(owner)) {
      throw new MaxCharactersReachedException();
    }
    Race race = getRaceByName(newCharacter.getRace().getName());
    newCharacter.setRace(modelMapper.map(race, RaceDTO.class));
    ruleService.executeRulesFor(newCharacter);
    DODCharacter characterEntity = modelMapper.map(newCharacter, DODCharacter.class);
    characterEntity.setOwner(owner);
    characterEntity = characterRepository.save(characterEntity);
    newCharacter.setId(characterEntity.getId());
    return newCharacter;
  }

  @CacheEvict(value = "characters", allEntries = true)
  @Transactional
  public void deleteCharacterByIdAndOwner(String characterId, String owner) {
    characterRepository.deleteByIdAndOwner(characterId, owner);
  }

  @Cacheable("characters")
  @Transactional
  public CharacterDTO findCharacterByIdAndOwner(String charId, String owner) {
    DODCharacter result = characterRepository
        .findByIdAndOwner(charId, owner)
        .orElseThrow(CharacterNotFoundException::new);
    return modelMapper.map(result, CharacterDTO.class);
  }

  @CacheEvict(value = "characters", allEntries = true)
  @Transactional
  public CharacterDTO save(CharacterDTO charUpdate, String owner) {
    DODCharacter characterEntity = modelMapper.map(charUpdate, DODCharacter.class);
    characterEntity.setBaseTraits(
        modelMapper.map(charUpdate.getBaseTraits(), new TypeToken<Map<BaseTraitName, BaseTrait>>() {}.getType()));
    characterEntity.setOwner(owner);
    characterRepository.save(characterEntity);
    return modelMapper.map(characterEntity, CharacterDTO.class);
  }

  @Transactional
  @Cacheable("characters")
  public List<CharacterDTO> fetchAllCharactersByOwner(String owner) {
    List<DODCharacter> entities = characterRepository.findAllByOwner(owner);
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
  public List<String> createCharacters(int bulkSize, String raceName, String owner) {
    //create a list of bulkSize CharacterDTOs
    List<String> result = new ArrayList<>();
    String nameBase = "bulk";
    for (int i = 0; i < bulkSize; i++) {
      result.add(createRandomCharacter(nameBase+"_1", raceName, owner));
    }
    return result;
  }

  @Transactional
  public String createRandomCharacter(String characterName, String raceName, String owner) {
    Race race = getRaceByName(raceName);

    CharacterDTO newCharacter = new CharacterDTO();
    newCharacter.setRace(modelMapper.map(race, RaceDTO.class));
    newCharacter.setName(characterName);
    newCharacter = createCharacter(newCharacter, owner);

    ruleService.executeRulesFor(newCharacter);

    DODCharacter characterEntity = modelMapper.map(newCharacter, DODCharacter.class);
    characterEntity.setOwner(owner);
    characterEntity = characterRepository.save(characterEntity);

    return characterEntity.getId();
  }

  public int getCharacterCountByOwner(String owner) {
    return characterRepository.countByOwner(owner);
  }

  public boolean isCharacterLimitReached(String owner) {
    return getCharacterCountByOwner(owner) >= configurationService.resolveMaxNpcsForUser(owner);
  }
}
