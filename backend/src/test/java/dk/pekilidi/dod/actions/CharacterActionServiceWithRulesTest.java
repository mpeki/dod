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
import dk.pekilidi.dod.data.SkillDTO;
import dk.pekilidi.dod.skill.SkillKey;
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
    SkillDTO characterSkill = new RandomObjectFiller().createAndFill(SkillDTO.class);
    SkillKey key = SkillKey.builder().value("primary.weapon").build();
    characterSkill.setKey(key);
    characterSkill.setFv(15);
    characterSkill.setExperience(0);
    Map<String, SkillDTO> skills = Map.of(key.getValue().getKeyValue(), characterSkill);
    testSubject.setSkills(skills);
    testSubject.setState(CharacterState.READY_TO_PLAY);

    //Train skill 100 times
    for (int i = 0; i < 100; i++) {
      SkillTrainingAction action = SkillTrainingAction
          .builder()
          .actor(testSubject)
          .type(Type.SKILL_TRAINING)
          .difficulty(Difficulty.NORMAL)
          .skillKey("primary.weapon")
          .build();
      int expBefore = action.getActor().getSkills().get("primary.weapon").getExperience();
      SkillTrainingAction actionAndResult = (SkillTrainingAction) cut.doAction(action);
      testSubject = actionAndResult.getActor();
      if (actionAndResult.getActionResult() == ActionResult.MASTERFUL
          || actionAndResult.getActionResult() == ActionResult.PERFECT) {
        int expAfter = actionAndResult.getActor().getSkills().get("primary.weapon").getExperience();
        assertTrue(expBefore < expAfter);
      }
      assertNotEquals(ActionResult.INVALID_ACTION, action.getActionResult());
      assertTrue(action.getResultDescription().contains("Tested "));
      System.out.println(testSubject.getSkills().get("primary.weapon").getExperience());
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