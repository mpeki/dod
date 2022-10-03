package dk.pekilidi.dod.changerequest;

import dk.pekilidi.dod.changerequest.model.ChangeRequest;
import dk.pekilidi.dod.changerequest.model.ChangeStatus;
import dk.pekilidi.dod.character.CharacterService;
import dk.pekilidi.dod.data.CharacterDTO;
import dk.pekilidi.dod.rules.DroolsService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChangeRequestService {

  @Autowired
  private CharacterService characterService;

  @Autowired
  private DroolsService ruleService;

  @Transactional
  public ChangeRequest submitChangeRequest(Long characterId, ChangeRequest change) {
    CharacterDTO character = characterService.findCharacterById(characterId);
    change = change.withObjectBeforeChange(character);
    ruleService.executeGroupFlowRulesFor(List.of(character, change), change.getChangeType().changeRuleSet);
    if (change.getStatus() == ChangeStatus.APPROVED) {
      characterService.save(character);
      return change.withObjectAfterChange(character);
    } else {
      return change;
    }
  }
}
