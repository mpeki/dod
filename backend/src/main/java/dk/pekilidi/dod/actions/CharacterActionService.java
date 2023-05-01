package dk.pekilidi.dod.actions;

import dk.pekilidi.dod.actions.model.Action;
import dk.pekilidi.dod.character.CharacterService;
import dk.pekilidi.dod.rules.DroolsService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CharacterActionService {

  @Autowired
  private DroolsService ruleService;

  public Action doAction(Action action) {
    log.debug("Executing action: {}", action);
    //Notice that the order of the rule flow groups is important, last one is the one that will be executed first
    int rulesExecuted = ruleService.executeGroupFlowRulesFor(List.of(action),"award-experience", action.getType().getValue());
    log.debug("Rules executed count:  {} rules", rulesExecuted);
    log.info("Executed action: {}", action);
    return action;
  }
}
