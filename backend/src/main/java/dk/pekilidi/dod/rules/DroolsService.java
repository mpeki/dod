package dk.pekilidi.dod.rules;

import dk.pekilidi.dod.data.DODFact;
import dk.pekilidi.dod.skill.SkillService;
import java.util.List;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.AgendaFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DroolsService {

  @Autowired
  private KieContainer kieContainer;

  @Autowired
  private SkillService skillService;

  public int executeRulesFor(DODFact fact) {
    return executeRulesFor(List.of(fact), null);
  }

  public int executeGroupFlowRulesFor(List<DODFact> dodFacts, String ruleFlowGroup) {
    KieSession kieSession = kieContainer.newKieSession();
    kieSession.setGlobal("skillService", skillService);
    kieSession.getAgenda().getAgendaGroup(ruleFlowGroup).setFocus();
    for (DODFact dodFact : dodFacts) {
      kieSession.insert(dodFact);
    }
    int numRulesFired = kieSession.fireAllRules();
    kieSession.dispose();
    return numRulesFired;
  }

  public int executeRulesFor(List<DODFact> dodFacts, AgendaFilter filter) {
    KieSession kieSession = kieContainer.newKieSession();
    for (DODFact dodFact : dodFacts) {
      kieSession.insert(dodFact);
    }
    int numRulesFired = kieSession.fireAllRules(filter);
    kieSession.dispose();
    return numRulesFired;
  }
}
