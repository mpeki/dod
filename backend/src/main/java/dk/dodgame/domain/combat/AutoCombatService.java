package dk.dodgame.domain.combat;

import dk.dodgame.data.combat.Fight;
import dk.dodgame.system.rule.DroolsService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutoCombatService {
    private final DroolsService ruleService;

    public AutoCombatService(DroolsService ruleService) {
        this.ruleService = ruleService;
    }

    @Transactional
//    @RabbitListener(queues = RabbitConfig.QUEUE_NAME)
    public void beginAutoFight(Fight... fights) {
        ruleService.executeGroupFlowRulesFor(List.of(fights), "static-auto-fight"); //, "melee-action"
        System.out.println("Auto fight completed");
    }
}
