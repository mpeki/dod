package dk.dodgame.domain.character;

import dk.dodgame.data.CharacterDTO;
import dk.dodgame.domain.character.model.BaseTrait;
import dk.dodgame.domain.character.model.BaseTraitName;
import dk.dodgame.domain.character.model.DODCharacter;
import dk.dodgame.domain.user.config.ConfigurationService;
import dk.dodgame.util.character.CharacterMapper;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import lombok.NonNull;
import org.modelmapper.TypeToken;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CharacterService {

  private static final CharacterMapper modelMapper = new CharacterMapper();
  private final CharacterFactory characterFactory;
  private final CharacterRepository characterRepository;
  private final ConfigurationService configurationService;

  public CharacterService(CharacterFactory characterFactory, CharacterRepository characterRepository,
      ConfigurationService configurationService) {
    this.characterFactory = characterFactory;
    this.characterRepository = characterRepository;
    this.configurationService = configurationService;
  }

  /**
   * Retrieves a list of DODCharacter objects by name.
   *
   * @param name The name of the characters to retrieve. Must not be null.
   * @return A list of DODCharacter objects that match the given name.
   * @throws CharacterNotFoundException If no characters matching the given name are found.
   */
  @Cacheable("characters")
  public List<DODCharacter> getCharactersByName(String name) {
    List<DODCharacter> chars = characterRepository.findAllByName(name);
    if (chars.isEmpty()) {
      throw new CharacterNotFoundException();
    }
    return chars;
  }

  /**
   * Creates a new character with the given information and assigns it to the specified owner.
   *
   * @param newCharacter The characterDTO object containing the details of the new character to be created. Must not
   *     be null.
   * @param owner The owner of the character. Must be a non-null string.
   * @return The CharacterDTO object representing the created character.
   * @throws MaxCharactersReachedException If the character limit for the specified owner has been reached.
   */
  @CacheEvict(value = "characters", allEntries = true)
  @Transactional
  public CharacterDTO createCharacter(@NonNull CharacterDTO newCharacter, @NonNull String owner) {
    if (isCharacterLimitReached(owner)) {
      throw new MaxCharactersReachedException();
    }
    newCharacter = characterFactory.createCharacterDTO(newCharacter);
    DODCharacter characterEntity = characterRepository.save(characterFactory.createDODCharacter(newCharacter, owner));
    newCharacter.setId(characterEntity.getId());
    return newCharacter;
  }

  /**
   * Creates a specified number of characters for a given race and assigns them to the specified owner.
   *
   * @param numberOfCharacters The number of characters to create. Must be a positive integer.
   * @param raceName The name of the race for the characters. Must not be null.
   * @param owner The owner of the characters. Must not be null.
   * @return A list of the IDs of the created characters.
   * @throws MaxCharactersReachedException If the character limit for the specified owner has been reached.
   */
  @CacheEvict(value = "characters", allEntries = true)
  @Transactional
  public List<String> createCharacters(int numberOfCharacters, String raceName, @NonNull String owner) {
    if (isCharacterLimitReached(owner)) {
      throw new MaxCharactersReachedException();
    }
    List<CharacterDTO> characterDTOList = characterFactory.createCharacterDTOs(numberOfCharacters, raceName);
    List<DODCharacter> characters = characterFactory.createDODCharacters(characterDTOList, owner);
    characterRepository.saveAll(characters);
    // Return a list of the ids of the created characters
    return characters.stream().map(DODCharacter::getId).toList();
  }

  /**
   * Deletes a character by its ID and owner.
   *
   * @param characterId The ID of the character to delete. Must not be null.
   * @param owner The owner of the character. Must not be null.
   */
  @CacheEvict(value = "characters", allEntries = true)
  @Transactional
  public void deleteCharacterByIdAndOwner(String characterId, String owner) {
    characterRepository.deleteByIdAndOwner(characterId, owner);
  }

  /**
   * Retrieves a CharacterDTO object by its ID and owner.
   *
   * @param charId The ID of the character to retrieve. Must not be null.
   * @param owner The owner of the character. Must not be null.
   * @return The CharacterDTO object representing the retrieved character.
   * @throws CharacterNotFoundException If no character with the specified ID and owner is found.
   */
  @Cacheable("characters")
  public CharacterDTO findCharacterByIdAndOwner(String charId, String owner) {
    DODCharacter result = characterRepository
        .findByIdAndOwner(charId, owner)
        .orElseThrow(CharacterNotFoundException::new);
    return modelMapper.map(result, CharacterDTO.class);
  }

  /**
   * Saves the given CharacterDTO object by converting it to a DODCharacter entity and persisting it to the database.
   * The saved character will be associated with the specified owner.
   *
   * @param charUpdate The CharacterDTO object to save. Must not be null.
   * @param owner The owner of the character. Must be a non-null string.
   * @return The CharacterDTO object representing the saved character.
   */
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

  /**
   * Retrieves a list of CharacterDTO objects by the owner.
   *
   * @param owner The owner of the characters to retrieve. Must not be null.
   * @return A list of CharacterDTO objects that belong to the specified owner.
   */
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
