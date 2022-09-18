package dk.pekilidi.dod.rules;

import dk.pekilidi.dod.data.DODFact;
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

  public int executeRulesFor(DODFact fact) {
    return executeRulesFor(List.of(fact), null);
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
