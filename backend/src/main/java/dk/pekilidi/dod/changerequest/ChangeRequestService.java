package dk.pekilidi.dod.changerequest;

import dk.pekilidi.dod.changerequest.model.ChangeRequest;
import dk.pekilidi.dod.character.CharacterService;
import dk.pekilidi.dod.data.CharacterDTO;
import dk.pekilidi.dod.rules.DroolsService;
import dk.pekilidi.dod.util.objects.CharacterMapper;
import java.util.List;
import org.drools.core.base.RuleNameStartsWithAgendaFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChangeRequestService {

  private static final CharacterMapper modelMapper = new CharacterMapper();
  @Autowired
  private CharacterService characterService;

  @Autowired
  private DroolsService ruleService;

  @Transactional
  public ChangeRequest submitChangeRequest(Long characterId, ChangeRequest change) {
    CharacterDTO character = characterService.findCharacterById(characterId);
    change = change.withObjectBeforeChange(character);
    ruleService.executeRulesFor(List.of(character, change), new RuleNameStartsWithAgendaFilter("Character change"));
    //    character.setBaseTraits(
    //        modelMapper.map(character.getBaseTraits(), new TypeToken<Map<BaseTraitName, BaseTrait>>() {}.getType()));
    characterService.save(character);
    return change.withObjectAfterChange(character);
  }
}
