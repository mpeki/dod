package dk.pekilidi.dod.character;

import dk.pekilidi.dod.character.data.CharacterDTO;
import dk.pekilidi.dod.character.data.DODFact;
import dk.pekilidi.dod.character.data.RaceDTO;
import dk.pekilidi.dod.character.model.BaseTrait;
import dk.pekilidi.dod.character.model.DODCharacter;
import dk.pekilidi.dod.character.model.Race;
import dk.pekilidi.dod.rules.changes.ChangeRequest;
import dk.pekilidi.dod.rules.changes.ChangeStatus;
import dk.pekilidi.dod.rules.changes.ChangeStatusLabel;
import dk.pekilidi.dod.util.objects.CharacterMapper;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.NonNull;
import org.drools.core.base.RuleNameStartsWithAgendaFilter;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.AgendaFilter;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DODCharacterService {

  private static final CharacterMapper modelMapper = new CharacterMapper();
  @Autowired
  private CharacterRepository characterRepository;
  @Autowired
  private RaceRepository raceRepository;
  @Autowired
  private KieContainer kieContainer;

  public DODCharacterService(CharacterRepository characterRepository) {
    this.characterRepository = characterRepository;
  }

  @Cacheable("characters")
  public List<DODCharacter> getCharactersByName(String name) {
    List<DODCharacter> chars = characterRepository.findByName(name);
    if (chars == null || chars.isEmpty()) {
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
    executeRulesFor(newCharacter);
    DODCharacter characterEntity = modelMapper.map(newCharacter, DODCharacter.class);
    characterEntity.setRace(race);
    characterEntity.setBaseTraits(characterEntity.getBaseTraits());
    characterEntity = characterRepository.save(characterEntity);
    newCharacter.setId(characterEntity.getId());
    return newCharacter;
  }

  @Cacheable("characters")
  @Transactional
  public CharacterDTO findCharacterById(Long charId) {
    DODCharacter result = checkOptional(characterRepository.findById(charId));
    return modelMapper.map(result, CharacterDTO.class);
  }

  public CharacterDTO save(CharacterDTO charUpdate) {
    DODCharacter characterEntity = modelMapper.map(charUpdate, DODCharacter.class);
    characterEntity.setBaseTraits(
        modelMapper.map(charUpdate.getBaseTraits(), new TypeToken<Map<BaseTraitName, BaseTrait>>() {}.getType()));
    characterRepository.save(characterEntity);
    return modelMapper.map(characterEntity, CharacterDTO.class);
  }

  @Transactional
  public ChangeRequest increaseBasetrait(Long characterId, ChangeRequest change) {
    DODCharacter character = checkOptional(characterRepository.findById(characterId));
    CharacterDTO characterDTO = modelMapper.map(character, CharacterDTO.class);
    change = change.withObjectBeforeChange(characterDTO);
    executeRulesFor(List.of(characterDTO, change), new RuleNameStartsWithAgendaFilter("Character change"));
    character = modelMapper.map(characterDTO, DODCharacter.class);
    character.setBaseTraits(
        modelMapper.map(characterDTO.getBaseTraits(), new TypeToken<Map<BaseTraitName, BaseTrait>>() {}.getType()));
    characterRepository.save(character);
    return change.withObjectAfterChange(characterDTO);
  }

  private int executeRulesFor(DODFact fact) {
    return executeRulesFor(List.of(fact), null);
  }

  private int executeRulesFor(List<DODFact> dodFacts, AgendaFilter filter) {
    KieSession kieSession = kieContainer.newKieSession();
    for (DODFact dodFact : dodFacts) {
      kieSession.insert(dodFact);
    }
    int numRulesFired = kieSession.fireAllRules(filter);
    kieSession.dispose();
    return numRulesFired;
  }

  private DODCharacter checkOptional(Optional<DODCharacter> optional) {
    if (optional.isPresent()) {
      return optional.get();
    } else {
      throw new CharacterNotFoundException();
    }
  }
}
