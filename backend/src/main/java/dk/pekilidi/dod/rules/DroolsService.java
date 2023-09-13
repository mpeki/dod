package dk.pekilidi.dod.rules;

import dk.pekilidi.dod.data.DODFact;
import dk.pekilidi.dod.items.ItemService;
import dk.pekilidi.dod.skill.SkillService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.kie.api.KieBase;
import org.kie.api.definition.KiePackage;
import org.kie.api.definition.rule.Rule;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.AgendaFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DroolsService {

  private final KieContainer kieContainer;
  private final SkillService skillService;
  private final ItemService itemService;

  @Autowired
  public DroolsService(SkillService skillService, ItemService itemService, KieContainer kieContainer) {
    this.skillService = skillService;
    this.itemService = itemService;
    this.kieContainer = kieContainer;
  }

  public int executeRulesFor(DODFact fact) {
    return executeRulesFor(List.of(fact), null);
  }

  public int executeGroupFlowRulesFor(List<DODFact> dodFacts, String... ruleFlowGroups) {
    KieBase kieBase = kieContainer.getKieBase();
    for (KiePackage kp : kieBase.getKiePackages()) {
      for (Rule rule : kp.getRules()) {
        log.debug("kp " + kp + " rule " + rule.getName());
      }
    }
    KieSession kieSession = kieContainer.newKieSession();
    kieSession.setGlobal("skillService", skillService);
    kieSession.setGlobal("itemService", itemService);

    for (String ruleFlowGroup : ruleFlowGroups) {
      kieSession.getAgenda().getAgendaGroup(ruleFlowGroup).setFocus();
    }
    for (DODFact dodFact : dodFacts) {
      kieSession.insert(dodFact);
    }
    int numRulesFired = kieSession.fireAllRules();
    kieSession.dispose();
    return numRulesFired;
  }

  public int executeRulesFor(List<DODFact> dodFacts, AgendaFilter filter) {
    KieSession kieSession = kieContainer.newKieSession();
    kieSession.setGlobal("itemService", itemService);
    for (DODFact dodFact : dodFacts) {
      kieSession.insert(dodFact);
    }
    int numRulesFired = kieSession.fireAllRules(filter);
    kieSession.dispose();
    return numRulesFired;
  }
}
