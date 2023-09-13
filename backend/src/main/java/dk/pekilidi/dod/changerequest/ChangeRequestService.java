package dk.pekilidi.dod.changerequest;

import dk.pekilidi.dod.changerequest.model.ChangeRequest;
import dk.pekilidi.dod.changerequest.model.ChangeStatus;
import dk.pekilidi.dod.changerequest.model.ChangeStatusLabel;
import dk.pekilidi.dod.character.CharacterService;
import dk.pekilidi.dod.data.CharacterDTO;
import dk.pekilidi.dod.rules.DroolsService;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChangeRequestService {

  private final CharacterService characterService;

  private DroolsService ruleService;

    public ChangeRequestService(CharacterService characterService) {
        this.characterService = characterService;
    }

    @Autowired
    public ChangeRequestService(DroolsService ruleService) {
        this.ruleService = ruleService;
    }

    @Transactional
  public ChangeRequest submitChangeRequest(@NotNull String characterId, ChangeRequest change) {
    CharacterDTO character = characterService.findCharacterById(characterId);
    change = change.withObjectBeforeChange(character);
    int noRulesFired = ruleService.executeGroupFlowRulesFor(List.of(character, change), change.getChangeType().changeRuleSet);
    if (noRulesFired == 0) {
      change = change.withStatus(ChangeStatus.REJECTED).withStatusLabel(ChangeStatusLabel.NO_RULES_FIRED);
    }
    if (change.getStatus() == ChangeStatus.APPROVED) {
      characterService.save(character);
      return change.withObjectAfterChange(character);
    } else {
      return change;
    }
  }
}
