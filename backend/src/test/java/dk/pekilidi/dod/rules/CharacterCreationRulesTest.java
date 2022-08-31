package dk.pekilidi.dod.rules;

import static org.junit.jupiter.api.Assertions.assertEquals;

import dk.pekilidi.dod.character.BaseTraitName;
import dk.pekilidi.dod.character.CharacterState;
import dk.pekilidi.dod.character.data.BaseTraitDTO;
import dk.pekilidi.dod.character.data.BaseTraitRuleDTO;
import dk.pekilidi.dod.character.data.CharacterDTO;
import dk.pekilidi.dod.character.data.CharacterTemplateDTO;
import dk.pekilidi.dod.character.data.RaceDTO;
import dk.pekilidi.dod.character.model.body.HumanoidBody;
import java.util.List;
import org.droolsassert.DroolsAssert;
import org.droolsassert.DroolsSession;
import org.droolsassert.TestRules;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@DroolsSession(resources = {"classpath:/rules/CharacterCreationRules.drl"},
    ignoreRules = {"before", "after"},
    logResources = true)
class CharacterCreationRulesTest {

  @RegisterExtension
  public DroolsAssert drools = new DroolsAssert();

  private CharacterDTO validNonHero;

  @BeforeEach
  void setup() {
    validNonHero = CharacterDTO
        .builder()
        .race(RaceDTO
            .builder()
            .characterTemplate(CharacterTemplateDTO
                .builder()
                .baseTraitRules(List.of(new BaseTraitRuleDTO(BaseTraitName.CONSTITUTION, "3t6", "2t6+6"),
                    new BaseTraitRuleDTO(BaseTraitName.SIZE, "3t6", "2t6+6")))
                .bodyTypeClass(HumanoidBody.class)
                .build())
            .build())
        .build();
  }

  @Test
  @TestRules(expected = {})
  void characterCreationDefaultCharacter() {
    CharacterDTO character = CharacterDTO.builder().build();
    drools.insert(character);
    drools.fireAllRules();
    drools.assertFactsCount(1);
  }

  @Test
  @TestRules(expected = {"Initialize base traits and hero points"},
      ignore = {"Set Group Value *", "Apply modifiers for age group *"})
  void characterCreationCharacterWithRace() {
    CharacterDTO character = CharacterDTO
        .builder()
        .race(RaceDTO
            .builder()
            .characterTemplate(CharacterTemplateDTO
                .builder()
                .baseTraitRules(List.of(
                    BaseTraitRuleDTO.builder().baseTraitName(BaseTraitName.STRENGTH).baseTraitDieRoll("1t20").build()))
                .build())
            .build())
        .build();
    drools.insert(character);
    drools.fireAllRules();
    drools.assertFactsCount(2);
  }

  @Test
  @TestRules(expected = {
      "Initialize base traits and hero points",
      "Initialize body - total HP",
      "Initialize body - body parts HP - humanoid",
      "Check character completion"}, ignore = {"Set Group Value *", "Apply modifiers for age group *"})
  void characterCreationCharacterWithRaceAndAbleToCalculateTotalHP() {
    drools.insert(validNonHero);
    drools.fireAllRules();
    drools.assertFactsCount(3);
    assertEquals(1, drools.getObjects(CharacterDTO.class).size());
    assertEquals(2, drools.getObjects(BaseTraitDTO.class).size());
    assertEquals(8, drools.getObjects(CharacterDTO.class).get(0).getBodyParts().size());
    CharacterDTO characterDTO = drools.getObject(CharacterDTO.class);
    assertEquals(CharacterState.INIT_COMPLETE, characterDTO.getState());
  }
}
