package dk.pekilidi.dod.rules;

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

  private final KieServices kieServices = KieServices.Factory.get();

  @Bean
  public KieContainer getKieContainer() {
    KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
    kieFileSystem.write(ResourceFactory.newClassPathResource("rules/CharacterCreationRules.drl"));
    kieFileSystem.write(ResourceFactory.newClassPathResource("rules/CharacterChangeRules.drl"));
    kieFileSystem.write(ResourceFactory.newClassPathResource("rules/BuyNewSkillRules.drl"));
    KieBuilder kb = kieServices.newKieBuilder(kieFileSystem);
    kb.buildAll();
    KieModule kieModule = kb.getKieModule();
    return kieServices.newKieContainer(kieModule.getReleaseId());
  }
}
