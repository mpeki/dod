package dk.pekilidi.dod.character;

import dk.pekilidi.dod.character.model.BaseTrait;
import dk.pekilidi.dod.character.model.BaseTraitName;
import dk.pekilidi.dod.character.model.DODCharacter;
import dk.pekilidi.dod.character.model.Race;
import dk.pekilidi.dod.data.CharacterDTO;
import dk.pekilidi.dod.data.RaceDTO;
import dk.pekilidi.dod.rules.DroolsService;
import dk.pekilidi.dod.util.objects.CharacterMapper;
import dk.pekilidi.dod.util.repo.OptionalCheck;
import java.util.List;
import java.util.Map;
import lombok.NonNull;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CharacterService {

  private static final CharacterMapper modelMapper = new CharacterMapper();
  @Autowired
  private CharacterRepository characterRepository;
  @Autowired
  private RaceRepository raceRepository;
  @Autowired
  private DroolsService ruleService;

  public CharacterService(CharacterRepository characterRepository) {
    this.characterRepository = characterRepository;
  }

  @Cacheable("characters")
  public List<DODCharacter> getCharactersByName(String name) {
    List<DODCharacter> chars = characterRepository.findByName(name);
    if (chars.isEmpty()) {
      throw new CharacterNotFoundException();
    }
    return chars;
  }

  @Cacheable("races")
  public Race getRaceByName(String name) {
    Race race = raceRepository.findByName(name);
    if (race == null) {
      throw new RaceNotFoundException();
    }
    return race;
  }

  @Transactional
  public CharacterDTO createCharacter(@NonNull CharacterDTO newCharacter) {
    Race race = getRaceByName(newCharacter.getRace().getName());
    newCharacter.setRace(modelMapper.map(race, RaceDTO.class));
    ruleService.executeRulesFor(newCharacter);
    DODCharacter characterEntity = modelMapper.map(newCharacter, DODCharacter.class);
    characterEntity.setRace(race);
    //    characterEntity.setBaseTraits(characterEntity.getBaseTraits());
    characterEntity = characterRepository.save(characterEntity);
    newCharacter.setId(characterEntity.getId());
    return newCharacter;
  }

  @Cacheable("characters")
  @Transactional
  public CharacterDTO findCharacterById(Long charId) {
    DODCharacter result = OptionalCheck.forDODCharacter(characterRepository.findById(charId));
    return modelMapper.map(result, CharacterDTO.class);
  }

  public CharacterDTO save(CharacterDTO charUpdate) {
    DODCharacter characterEntity = modelMapper.map(charUpdate, DODCharacter.class);
    characterEntity.setBaseTraits(
        modelMapper.map(charUpdate.getBaseTraits(), new TypeToken<Map<BaseTraitName, BaseTrait>>() {}.getType()));
    characterRepository.save(characterEntity);
    return modelMapper.map(characterEntity, CharacterDTO.class);
  }
}