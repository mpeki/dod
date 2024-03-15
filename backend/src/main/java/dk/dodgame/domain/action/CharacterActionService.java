package dk.dodgame.domain.action;

import dk.dodgame.domain.action.model.Action;
import dk.dodgame.system.rule.DroolsService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CharacterActionService {

  private final DroolsService ruleService;

  public CharacterActionService(DroolsService ruleService) {
    this.ruleService = ruleService;
  }

  public Action doAction(Action action) {
    if (action == null) {
      throw new IllegalArgumentException("Action cannot be null");
    }
    log.debug("Executing action of type: {}, and dificulty: {}, for actor: {}.", action.getType(),
        action.getDifficulty(), action.getActor());

    //Notice that the order of the rule flow groups is important, last one is the one that will be executed first
    int rulesExecuted = ruleService.executeGroupFlowRulesFor(List.of(action), "award-experience", action.getType().getValue());

    log.debug("Rules executed count:  {} rules", rulesExecuted);
    log.info("Result: {} - {}", action.getActionResult(), action.getResultDescription());

    return action;
  }
}
