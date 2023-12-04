package dk.dodgame.system.rule;

import lombok.extern.slf4j.Slf4j;
import org.kie.api.event.rule.AfterMatchFiredEvent;
import org.kie.api.event.rule.DefaultAgendaEventListener;

@Slf4j
public class LoggingAgendaEventListener extends DefaultAgendaEventListener {

  @Override
  public void afterMatchFired(AfterMatchFiredEvent event) {
    String ruleName = event.getMatch().getRule().getName();
    log.info("Rule fired: {}", ruleName);
  }
}
