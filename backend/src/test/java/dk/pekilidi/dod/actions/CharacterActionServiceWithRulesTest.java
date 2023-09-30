package dk.pekilidi.dod.actions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dk.pekilidi.dod.DodApplication;
import dk.pekilidi.dod.actions.model.Action;
import dk.pekilidi.dod.actions.model.ActionResult;
import dk.pekilidi.dod.actions.model.Difficulty;
import dk.pekilidi.dod.actions.model.Type;
import dk.pekilidi.dod.actions.model.SkillTrainingAction;
import dk.pekilidi.dod.character.model.CharacterState;
import dk.pekilidi.dod.data.CharacterDTO;
import dk.pekilidi.dod.data.CharacterSkillDTO;
import dk.pekilidi.dod.data.SkillDTO;
import dk.pekilidi.dod.skill.SkillKey;
import dk.pekilidi.dod.skill.SkillService;
import dk.pekilidi.utils.RandomObjectFiller;
import java.util.Map;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = DodApplication.class)
@Tag("regression")
class CharacterActionServiceWithRulesTest {

  @Autowired
  private CharacterActionService cut;

  @Autowired
  private SkillService skillService;

  public static final String TEST_SKILL_KEY = "primary.weapon";

  @Test
  void testSingleSourceNoTargetAction_training_character_not_activated() throws Exception {
    CharacterDTO testSubject = new RandomObjectFiller().createAndFill(CharacterDTO.class);
    testSubject.setState(CharacterState.DEAD);
    Action action = SkillTrainingAction
        .builder()
        .actor(testSubject)
        .type(Type.SKILL_TRAINING)
        .difficulty(Difficulty.EASY)
        .build();
    cut.doAction(action);
    assertEquals(ActionResult.INVALID_ACTION, action.getActionResult());
    assertTrue(action.getResultDescription().contains("Character is not ready to play"));
  }

  @Test
  void testSingleSourceNoTargetAction_training_skill_does_not_exist() throws Exception {
    CharacterDTO testSubject = new RandomObjectFiller().createAndFill(CharacterDTO.class);
    testSubject.setState(CharacterState.READY_TO_PLAY);
    Action action = SkillTrainingAction
        .builder()
        .actor(testSubject)
        .type(Type.SKILL_TRAINING)
        .difficulty(Difficulty.NORMAL)
        .build();
    cut.doAction(action);
    assertEquals(ActionResult.INVALID_ACTION, action.getActionResult());
    assertTrue(action.getResultDescription().contains("Skill does not exist"));
  }

  @Test
  void testSingleSourceNoTargetAction_training_skill() throws Exception {
    CharacterDTO testSubject = new RandomObjectFiller().createAndFill(CharacterDTO.class);
    CharacterSkillDTO characterSkill = new RandomObjectFiller().createAndFill(CharacterSkillDTO.class);
    SkillKey key = SkillKey.builder().value(TEST_SKILL_KEY).build();
    characterSkill.setSkill(SkillDTO.builder().key(key).build());;
    characterSkill.setFv(15);
    characterSkill.setExperience(0);
    Map<String, CharacterSkillDTO> skills = Map.of(key.getValue().getKeyValue(), characterSkill);
    testSubject.setSkills(skills);
    testSubject.setState(CharacterState.READY_TO_PLAY);

    //Train skill 100 times
    for (int i = 0; i < 100; i++) {
      SkillTrainingAction action = SkillTrainingAction
          .builder()
          .actor(testSubject)
          .type(Type.SKILL_TRAINING)
          .difficulty(Difficulty.NORMAL)
          .skillKey(TEST_SKILL_KEY)
          .skill(skillService.findSkillByKey(TEST_SKILL_KEY))
          .build();
      int expBefore = action.getActor().getSkills().get(TEST_SKILL_KEY).getExperience();
      SkillTrainingAction actionAndResult = (SkillTrainingAction) cut.doAction(action);
      testSubject = actionAndResult.getActor();
      if (actionAndResult.getActionResult() == ActionResult.MASTERFUL
          || actionAndResult.getActionResult() == ActionResult.PERFECT) {
        int expAfter = actionAndResult.getActor().getSkills().get(TEST_SKILL_KEY).getExperience();
        assertTrue(expBefore < expAfter);
      }
      assertNotEquals(ActionResult.INVALID_ACTION, action.getActionResult());
      assertTrue(action.getResultDescription().contains("Rolled ["));
      System.out.println(testSubject.getSkills().get(TEST_SKILL_KEY).getExperience());
    }
    System.out.println(testSubject);
  }

  @Test
  void throwsIllegalArgumentException_whenActionIsNull() {
    Action action = null;
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      cut.doAction(action);
    });
    assertEquals("Action cannot be null", exception.getMessage());
  }
}