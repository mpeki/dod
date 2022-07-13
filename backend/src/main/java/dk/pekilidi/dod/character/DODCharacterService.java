package dk.pekilidi.dod.character;

import dk.pekilidi.dod.character.data.CharacterDTO;
import dk.pekilidi.dod.character.data.DODFact;
import dk.pekilidi.dod.character.data.RaceDTO;
import dk.pekilidi.dod.character.model.BaseTrait;
import dk.pekilidi.dod.character.model.DODCharacter;
import dk.pekilidi.dod.character.model.Race;
import dk.pekilidi.dod.rules.changes.ChangeRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.drools.core.base.RuleNameStartsWithAgendaFilter;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.AgendaFilter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DODCharacterService {

  private final ModelMapper modelMapper = new ModelMapper();
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
  public CharacterDTO createCharacter(CharacterDTO newCharacter) {
    Race race = getRaceByName(newCharacter.getRace().getName());
    newCharacter.setRace(modelMapper.map(race, RaceDTO.class));
    executeRulesFor(newCharacter);
    DODCharacter characterEntity = modelMapper.map(newCharacter, DODCharacter.class);
    characterEntity.setRace(race);
    characterEntity.setBaseTraits(characterEntity.getBaseTraits());
    characterEntity = characterRepository.save(characterEntity);
    newCharacter = modelMapper.map(characterEntity, CharacterDTO.class);
    return newCharacter;
  }

  @Cacheable("characters")
  public CharacterDTO findCharacterById(Long charId) {
    DODCharacter result = checkOptional(characterRepository.findById(charId));
    return modelMapper.map(result, CharacterDTO.class);
  }

    public CharacterDTO save(CharacterDTO charUpdate) {
      DODCharacter characterEntity = modelMapper.map(charUpdate, DODCharacter.class);
      characterEntity.setBaseTraits(modelMapper.map(charUpdate.getBaseTraits(),
          new TypeToken<Map<BaseTraitName,BaseTrait>>() {}.getType()));
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
    character.setBaseTraits(modelMapper.map(characterDTO.getBaseTraits(), new TypeToken<Map<BaseTraitName,BaseTrait>>() {}.getType()));
    characterRepository.save(character);
    return change.withObjectAfterChange(characterDTO);
  }

  private void executeRulesFor(DODFact fact){
    executeRulesFor(List.of(fact), null);
  }
  private void executeRulesFor(List<DODFact> dodFacts, AgendaFilter filter){
    KieSession kieSession = kieContainer.newKieSession();
    for (DODFact dodFact : dodFacts) {
      kieSession.insert(dodFact);
    }
    int numRulesFired = kieSession.fireAllRules(filter);
    System.out.println("numRulesFired = " + numRulesFired);
    kieSession.dispose();
  }

  private DODCharacter checkOptional(Optional<DODCharacter> optional){
    if (optional.isPresent()) {
      return optional.get();
    } else {
      throw new CharacterNotFoundException();
    }
  }
}
