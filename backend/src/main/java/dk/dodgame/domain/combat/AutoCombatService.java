package dk.dodgame.domain.combat;

import java.util.List;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

import dk.dodgame.data.DODFact;
import dk.dodgame.data.combat.Fight;
import dk.dodgame.data.combat.FightState;
import dk.dodgame.system.rule.DroolsService;

@Service
@Slf4j
public class AutoCombatService {
	private final DroolsService ruleService;

	public AutoCombatService(DroolsService ruleService) {
		this.ruleService = ruleService;
	}

	/**
	 * Executes the static auto fight rules until all supplied fights have
	 * reached {@link FightState#DONE}. Each iteration creates a new Drools
	 * session via {@link DroolsService#executeGroupFlowRulesFor} because a
	 * single session does not re-trigger the "New Turn" rule after the
	 * "End Turn" rule updates the fight.
	 */
	@Transactional
	//    @RabbitListener(queues = RabbitConfig.QUEUE_NAME)
	public void beginAutoFight(Fight... fights) {
		List<DODFact> ongoing = new java.util.ArrayList<>(List.of(fights));
		boolean pending = true;
		while (pending) {
			ruleService.executeGroupFlowRulesFor(ongoing, "static-auto-fight");
			pending = ongoing.stream().map(f -> (Fight) f)
					.anyMatch(f -> f.getFightPhase() != FightState.DONE);
		}
		log.debug("Auto fight completed");
	}
}
