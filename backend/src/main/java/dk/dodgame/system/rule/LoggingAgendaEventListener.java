package dk.dodgame.system.rule;

import org.kie.api.event.rule.BeforeMatchFiredEvent;
import org.kie.api.event.rule.DefaultAgendaEventListener;

import lombok.extern.slf4j.Slf4j;

import dk.dodgame.data.combat.Fight;

@Slf4j
public class LoggingAgendaEventListener extends DefaultAgendaEventListener {

	@Override
	public void beforeMatchFired(BeforeMatchFiredEvent event) {
		String ruleName = event.getMatch().getRule().getName();

		Fight fight = event.getMatch().getObjects().stream()
				.filter(Fight.class::isInstance)
				.map(Fight.class::cast)
				.findFirst()
				.orElse(null);

		if(fight != null){
			if("New Turn".equals(ruleName)) {
				log.debug("====== Starting New Turn no. {} ({}) ======", fight.getTurnCounter() + 1, fight.getRef());
			} else {
				log.debug("[{}] Entering Phase: {}", fight.getRef(), ruleName);
			}
		}
	}

//	@Override
//	public void afterMatchFired(AfterMatchFiredEvent event) {
//		String ruleName = event.getMatch().getRule().getName();
//		log.debug("==== Rule finished: {} ====", ruleName);
//	}
}
