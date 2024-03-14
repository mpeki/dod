package dk.dodgame.domain.changerequest;

import dk.dodgame.data.CharacterDTO;
import dk.dodgame.data.PaymentDTO;
import dk.dodgame.domain.changerequest.model.ChangeItem;
import dk.dodgame.domain.changerequest.model.ChangeRequest;
import dk.dodgame.domain.changerequest.model.ChangeStatus;
import dk.dodgame.domain.changerequest.model.ChangeStatusLabel;
import dk.dodgame.domain.changerequest.model.ChangeType;
import dk.dodgame.domain.changerequest.model.SecondaryChangeKey;
import dk.dodgame.domain.character.CharacterService;
import dk.dodgame.system.rule.DroolsService;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChangeRequestService {

  private final CharacterService characterService;

  private final DroolsService ruleService;

  @Autowired
  public ChangeRequestService(DroolsService ruleService, CharacterService characterService) {
    this.ruleService = ruleService;
    this.characterService = characterService;
  }

  @Transactional
  public ChangeRequest submitChangeRequest(@NotNull String characterId, ChangeRequest change, String owner) {
    CharacterDTO character = characterService.findCharacterByIdAndOwner(characterId, owner);

    change = change.withObjectBeforeChange(character);

    int noRulesFired = ruleService.executeGroupFlowRulesFor(
        List.of(character, change), change.getChangeType().changeRuleSet);
    if (noRulesFired == 0) {
      change = change.withStatus(ChangeStatus.REJECTED).withStatusLabel(ChangeStatusLabel.NO_RULES_FIRED);
    }
    if (change.getStatus() == ChangeStatus.APPROVED) {
      characterService.save(character, owner);
      return change.withObjectAfterChange(character);
    } else {
      return change;
    }
  }
}
