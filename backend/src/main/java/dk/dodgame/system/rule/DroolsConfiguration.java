package dk.dodgame.system.rule;

import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.runtime.KieContainer;
import org.kie.internal.io.ResourceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DroolsConfiguration {

  private final KieServices kieServices = KieServices.get();

  @Bean
  public KieContainer getKieContainer() {
    KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
    kieFileSystem.write(ResourceFactory.newClassPathResource("rules/action/SingleSourceActionRules.drl"));
    kieFileSystem.write(ResourceFactory.newClassPathResource("rules/character/CharacterCreationRules.drl"));
    kieFileSystem.write(ResourceFactory.newClassPathResource("rules/character/CharacterChangeRules.drl"));
    kieFileSystem.write(ResourceFactory.newClassPathResource("rules/skill/AwardExperienceRules.drl"));
    kieFileSystem.write(ResourceFactory.newClassPathResource("rules/skill/BuyNewSkillRules.drl"));
    kieFileSystem.write(ResourceFactory.newClassPathResource("rules/skill/RemoveSkillRules.drl"));
    kieFileSystem.write(ResourceFactory.newClassPathResource("rules/skill/BuySkillFVIncreaseRules.drl"));
    kieFileSystem.write(ResourceFactory.newClassPathResource("rules/item/BuyNewItemCharInitRules.drl"));
    kieFileSystem.write(ResourceFactory.newClassPathResource("rules/item/RemoveItemCharInitRules.drl"));
    KieBuilder kb = kieServices.newKieBuilder(kieFileSystem);
    kb.buildAll();
    KieModule kieModule = kb.getKieModule();
    return kieServices.newKieContainer(kieModule.getReleaseId());
  }
}
