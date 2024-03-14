package dk.dodgame.rule;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import dk.dodgame.data.BaseTraitDTO;
import dk.dodgame.data.BaseTraitRuleDTO;
import dk.dodgame.data.CharacterDTO;
import dk.dodgame.data.CharacterTemplateDTO;
import dk.dodgame.data.ItemDTO;
import dk.dodgame.data.RaceDTO;
import dk.dodgame.data.SkillDTO;
import dk.dodgame.domain.character.model.BaseTraitName;
import dk.dodgame.domain.character.model.CharacterState;
import dk.dodgame.domain.character.model.body.BodyPartName;
import dk.dodgame.domain.character.model.body.HumanoidBody;
import dk.dodgame.domain.item.ItemKey;
import dk.dodgame.domain.item.ItemService;
import dk.dodgame.domain.skill.SkillKey;
import dk.dodgame.domain.skill.SkillService;
import java.util.List;
import org.droolsassert.DroolsAssert;
import org.droolsassert.DroolsSession;
import org.droolsassert.TestRules;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@DroolsSession(resources = {"classpath:/rules/character/CharacterCreationRules.drl"},
    ignoreRules = {"before", "after"},
    logResources = true)
@Tag("regression")
class CharacterCreationRulesTest {

  @RegisterExtension
  public DroolsAssert drools = new DroolsAssert();

  private CharacterDTO validNonHero;

  @BeforeEach
  void setup() {
    ItemService itemService = mock(ItemService.class);
    SkillService skillService = mock(SkillService.class);
    when(itemService.findItemByKey("gold")).thenReturn(ItemDTO.builder().itemKey(ItemKey.toItemKey("gold")).build());
    when(itemService.findItemByKey("silver")).thenReturn(
        ItemDTO.builder().itemKey(ItemKey.toItemKey("silver")).build());
    when(skillService.findSkillByKey("dodge")).thenReturn(SkillDTO.builder().key(SkillKey.toSkillKey("dodge")).build());
    when(skillService.findSkillByKey("hand.to.hand")).thenReturn(SkillDTO.builder().key(SkillKey.toSkillKey("hand.to.hand")).build());
    drools.setGlobal("itemService", itemService);
    drools.setGlobal("skillService", skillService);
    validNonHero = CharacterDTO
        .builder()
        .race(RaceDTO
            .builder()
            .name("human")
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
  @TestRules(expected = {
      "Determine favorite hand",
      "Determine social status - Humans",
      "Initialize movement point",
      "Add natural skills",
      "Set Looks"})
  void characterCreationDefaultCharacter() {
    BaseTraitDTO size = BaseTraitDTO
        .builder()
        .traitName(BaseTraitName.SIZE)
        .startValue(10)
        .currentValue(10)
        .groupValue(2)
        .build();
    CharacterDTO character = CharacterDTO.builder().race(RaceDTO.builder().name("human").build()).build();
    character.addBaseTrait(size);
    drools.insert(character);
    drools.fireAllRules();
    drools.assertFactsCount(1);
  }

  @Test
  @TestRules(expected = {
      "Initialize base traits and hero points",
      "Determine social status - Dwarfs",
      "Determine favorite hand",
      "Initialize movement point",
      "Set Looks",
      "Add natural skills"}, ignore = {"Set Group Value *", "Apply modifiers for age group *"})
  void characterCreationCharacterWithRace() {
    CharacterDTO character = CharacterDTO
        .builder()
        .race(RaceDTO
            .builder()
            .name("dwarf")
            .characterTemplate(CharacterTemplateDTO.builder().baseTraitRules(List.of(
                BaseTraitRuleDTO.builder().baseTraitName(BaseTraitName.STRENGTH).baseTraitDieRoll("1t20").build(),
                BaseTraitRuleDTO.builder().baseTraitName(BaseTraitName.SIZE).baseTraitDieRoll("1t20").build())).build())
            .build())
        .build();
    drools.insert(character);
    drools.fireAllRules();
    drools.assertFactsCount(3);
  }

  @Test
  @TestRules(expected = {
      "Initialize base traits and hero points",
      "Determine social status - Dwarfs",
      "Determine favorite hand",
      "Initialize movement point",
      "Set Looks",
      "Apply modifiers for age group MATURE",
      "Add natural skills"})
  void characterCreationCharacterWithFreeSkills() {
    CharacterDTO character = CharacterDTO
        .builder()
        .race(RaceDTO
            .builder()
            .name("dwarf")
            .characterTemplate(CharacterTemplateDTO.builder().baseTraitRules(List.of(
                BaseTraitRuleDTO.builder().baseTraitName(BaseTraitName.STRENGTH).baseTraitDieRoll("1t20").build(),
                BaseTraitRuleDTO.builder().baseTraitName(BaseTraitName.DEXTERITY).baseTraitDieRoll("1t20").build(),
                BaseTraitRuleDTO.builder().baseTraitName(BaseTraitName.SIZE).baseTraitDieRoll("1t20").build())).build())
            .build())
        .build();
    drools.insert(character);
    drools.fireAllRules();
    drools.assertFactsCount(4);
    CharacterDTO characterDTO = drools.getObject(CharacterDTO.class);
    assertEquals(2, characterDTO.getSkills().size());
  }

  @Test
  @TestRules(expected = {
      "Initialize base traits and hero points",
      "Initialize body - total HP",
      "Initialize body - body parts HP - humanoid",
      "Determine favorite hand",
      "Check character completion",
      "Determine social status - Humans",
      "Initialize damage bonus",
      "Initialize movement point",
      "Add natural skills",
      "Set Looks"}, ignore = {"Set Group Value *", "Apply modifiers for age group *"})
  void characterCreationCharacterWithRaceAndAbleToCalculateTotalHP() {
    drools.insert(validNonHero);
    drools.fireAllRules();
    drools.assertFactsCount(3);
    assertEquals(1, drools.getObjects(CharacterDTO.class).size());
    assertEquals(2, drools.getObjects(BaseTraitDTO.class).size());
    assertEquals(8, drools.getObjects(CharacterDTO.class).get(0).getBodyParts().size());
    CharacterDTO characterDTO = drools.getObject(CharacterDTO.class);
    //Prove DOD-286 is fixed
    assertEquals(
        characterDTO.getBodyParts().get(BodyPartName.LEFT_LEG).getMaxHP(),
        characterDTO.getBodyParts().get(BodyPartName.RIGHT_LEG).getMaxHP());
    assertEquals(
        characterDTO.getBodyParts().get(BodyPartName.LEFT_ARM).getMaxHP(),
        characterDTO.getBodyParts().get(BodyPartName.RIGHT_ARM).getMaxHP());
    Assertions.assertEquals(CharacterState.INIT_COMPLETE, characterDTO.getState());
  }

  @ParameterizedTest
  @CsvSource({
      "1, '-1t2'",
      "4, '-1t2'",
      "5, '-1'",
      "13, 'none'",
      "16, '+1t2'",
      "34, '+2t6'",
      "46, '+3t8'",
      "71, '+6t6'",
      "80, '+6t6'",
      "81, '+7t6'",
      "90, '+7t6'",
      "91, '+8t6'",
      "100, '+8t6'",
      "101, '+9t6'",
      "104, '+9t6'",
      "110, '+9t6'",
      "120, '+10t6'"})
  @TestRules(expected = {
      "Check character completion",
      "Determine favorite hand",
      "Determine social status - Humans",
      "Initialize damage bonus",
      "Initialize movement point",
      "Add natural skills",
      "Set Looks"}, ignore = {"Set Group Value *", "Apply modifiers for age group *"})
  void characterCreationCharacterTestDamageBonusLimits(int averageVal, String damageBonus) {
    validNonHero.getBaseTraits().put(BaseTraitName.STRENGTH, new BaseTraitDTO(BaseTraitName.STRENGTH, averageVal, 1));
    validNonHero.getBaseTraits().put(BaseTraitName.SIZE, new BaseTraitDTO(BaseTraitName.SIZE, averageVal, 1));
    validNonHero.setState(CharacterState.BODY_PART_HP_SET);
    drools.insert(validNonHero);
    drools.fireAllRules();
    drools.assertFactsCount(1);
    CharacterDTO characterDTO = drools.getObject(CharacterDTO.class);
    assertEquals(damageBonus, characterDTO.getDamageBonus());
    Assertions.assertEquals(CharacterState.INIT_COMPLETE, characterDTO.getState());
  }
}
