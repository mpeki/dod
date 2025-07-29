package dk.dodgame.domain.actions;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import dk.dodgame.DodApplication;
import dk.dodgame.data.CharacterDTO;
import dk.dodgame.data.CharacterSkillDTO;
import dk.dodgame.data.SkillDTO;
import dk.dodgame.domain.action.CharacterActionService;
import dk.dodgame.domain.action.model.*;
import dk.dodgame.domain.character.model.CharacterState;
import dk.dodgame.domain.skill.SkillKey;
import dk.dodgame.domain.skill.SkillService;
import dk.dodgame.util.RandomObjectFiller;

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
    Assertions.assertEquals(ActionResult.INVALID_ACTION, action.getActionResult());
    assertTrue(action.getResultDescription().getActionResult().contains("Character is not ready to play"));
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
    assertTrue(action.getResultDescription().getActionResult().contains("Skill does not exist"));
  }

  @Test
  void testSingleSourceNoTargetAction_training_skill() throws Exception {
    CharacterDTO testSubject = new RandomObjectFiller().createAndFill(CharacterDTO.class);
    CharacterSkillDTO characterSkill = new RandomObjectFiller().createAndFill(CharacterSkillDTO.class);
    SkillKey key = SkillKey.builder().value(TEST_SKILL_KEY).build();
    characterSkill.setSkill(SkillDTO.builder().key(key).build());
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
      int expBefore = ((CharacterDTO) action.getActor()).getSkills().get(TEST_SKILL_KEY).getExperience();
      SkillTrainingAction actionAndResult = (SkillTrainingAction) cut.doAction(action);
      testSubject = (CharacterDTO)actionAndResult.getActor();
      if (actionAndResult.getActionResult() == ActionResult.MASTERFUL
          || actionAndResult.getActionResult() == ActionResult.PERFECT) {
        int expAfter = ((CharacterDTO)actionAndResult.getActor()).getSkills().get(TEST_SKILL_KEY).getExperience();
        assertTrue(expBefore < expAfter);
      }
      assertNotEquals(ActionResult.INVALID_ACTION, action.getActionResult());
      assertTrue(action.getResultDescription().getActionResult().contains("Rolled ["));
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