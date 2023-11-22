package dk.dodgame.domain.character;

import dk.dodgame.data.CharacterDTO;
import dk.dodgame.domain.character.model.BaseTrait;
import dk.dodgame.domain.character.model.BaseTraitName;
import dk.dodgame.domain.character.model.DODCharacter;
import dk.dodgame.domain.user.config.ConfigurationService;
import dk.dodgame.data.RaceDTO;
import dk.dodgame.domain.race.RaceNotFoundException;
import dk.dodgame.domain.race.RaceRepository;
import dk.dodgame.domain.race.model.Race;
import dk.dodgame.system.rule.DroolsService;
import dk.dodgame.util.character.CharacterMapper;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import lombok.NonNull;
import org.modelmapper.TypeToken;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CharacterService {

  private final CharacterFactory characterFactory;
  private static final CharacterMapper modelMapper = new CharacterMapper();
  private final CharacterRepository characterRepository;
  private final RaceRepository raceRepository;
  private final DroolsService ruleService;
  private final ConfigurationService configurationService;

  public CharacterService(CharacterFactory characterFactory, CharacterRepository characterRepository, RaceRepository raceRepository,
      DroolsService ruleService, ConfigurationService configurationService) {
    this.characterFactory = characterFactory;
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
  @Transactional
  public CharacterDTO createCharacter(@NonNull CharacterDTO newCharacter,@NonNull String owner) {
    if(isCharacterLimitReached(owner)) {
      throw new MaxCharactersReachedException();
    }
    newCharacter = characterFactory.createCharacterDTO(newCharacter);
    DODCharacter characterEntity = characterRepository.save(characterFactory.createDODCharacter(newCharacter, owner));
    newCharacter.setId(characterEntity.getId());
    return newCharacter;
  }

  @CacheEvict(value = "characters", allEntries = true)
  @Transactional
  public List<String> createCharacters(int numberOfCharacters, String raceName, @NonNull String owner) {
    if(isCharacterLimitReached(owner)) {
      throw new MaxCharactersReachedException();
    }
    List<CharacterDTO> characterDTOList = characterFactory.createCharacterDTOs(numberOfCharacters, raceName);
    List<DODCharacter> characters = characterFactory.createDODCharacters(characterDTOList, owner);
    characterRepository.saveAll(characters);
    // Return a list of the ids of the created characters
    return characters.stream()
        .map(DODCharacter::getId)
        .toList();

  }

  @CacheEvict(value = "characters", allEntries = true)
  @Transactional
  public void deleteCharacterByIdAndOwner(String characterId, String owner) {
    characterRepository.deleteByIdAndOwner(characterId, owner);
  }

  @Cacheable("characters")
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

  @Cacheable("characters")
  public List<CharacterDTO> fetchAllCharactersByOwner(String owner) {
    List<DODCharacter> entities = characterRepository.findAllByOwner(owner);
    return Arrays.stream(entities.toArray()).map(object -> modelMapper.map(object, CharacterDTO.class)).toList();
  }

  public int getCharacterCountByOwner(String owner) {
    return characterRepository.countByOwner(owner);
  }

  public boolean isCharacterLimitReached(String owner) {
    return getCharacterCountByOwner(owner) >= configurationService.resolveMaxNpcsForUser(owner);
  }
}
