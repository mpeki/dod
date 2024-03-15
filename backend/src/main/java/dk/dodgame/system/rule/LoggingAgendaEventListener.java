package dk.dodgame.system.rule;

import org.kie.api.event.rule.AfterMatchFiredEvent;
import org.kie.api.event.rule.BeforeMatchFiredEvent;
import org.kie.api.event.rule.DefaultAgendaEventListener;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoggingAgendaEventListener extends DefaultAgendaEventListener {

	@Override
	public void beforeMatchFired(BeforeMatchFiredEvent event) {
		String ruleName = event.getMatch().getRule().getName();
		log.debug("==== Rule Match: {} ====", ruleName);
	}

	@Override
	public void afterMatchFired(AfterMatchFiredEvent event) {
		String ruleName = event.getMatch().getRule().getName();
		log.debug("==== Rule finished: {} ====", ruleName);
	}
}
