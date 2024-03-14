package dk.dodgame.rule;

import static org.junit.jupiter.api.Assertions.assertEquals;

import dk.dodgame.data.CharacterDTO;
import dk.dodgame.domain.changerequest.model.ChangeRequest;
import dk.dodgame.domain.changerequest.model.ChangeStatus;
import dk.dodgame.domain.changerequest.model.ChangeStatusLabel;
import dk.dodgame.domain.changerequest.model.ChangeType;
import dk.dodgame.domain.character.model.CharacterState;
import dk.dodgame.domain.skill.SkillKey;
import org.droolsassert.DroolsAssert;
import org.droolsassert.DroolsSession;
import org.droolsassert.TestRules;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DroolsSession(resources = {"classpath:/rules/character/CharacterChangeRules.drl", "classpath:/rules/skill/BuyNewSkillRules.drl"},
    ignoreRules = {"before", "after"},
    logResources = true)
@Tag("regression")
public class CharacterChangeRulesTest {

  @RegisterExtension
  public DroolsAssert drools = new DroolsAssert();

  private CharacterDTO playableCharacter;
  private ChangeRequest changeRequest;

  @BeforeEach
  void setup() {
    playableCharacter = CharacterDTO.builder().state(CharacterState.INIT_COMPLETE).baseSkillPoints(4).build();
    changeRequest = ChangeRequest
        .builder()
        .changeType(ChangeType.CHARACTER_READY_TO_PLAY)
        .objectBeforeChange(playableCharacter)
        .changeKey(CharacterState.READY_TO_PLAY)
        .build();
  }

  @Test
  @TestRules(expected = {"Character change - make character ready for play"})
  void characterChangeMakeReadyForPlay() {
    drools.insert(playableCharacter);
    drools.insert(changeRequest);
    drools.getSession().getAgenda().getAgendaGroup("character-state-change").setFocus();
    drools.fireAllRules();
    drools.assertFactsCount(2);
    ChangeRequest change = drools.getObject(ChangeRequest.class);
    CharacterDTO characterDTO = drools.getObject(CharacterDTO.class);
    Assertions.assertEquals(ChangeStatus.APPROVED, change.getStatus());
    Assertions.assertEquals(ChangeStatusLabel.OK_READY_FOR_PLAY, change.getStatusLabel());
    Assertions.assertEquals(CharacterState.READY_TO_PLAY, characterDTO.getState());
  }

  @ParameterizedTest
  @ValueSource(strings = {"NEW", "BASE_TRAITS_SET", "AGE_GROUP_SET", "TOTAL_HP_SET", "DEAD"})
  @TestRules(expected = {"Character change - make character ready for play - wrong state"})
  void characterChangeMakeReadyForPlayIncorrectState(CharacterState illegalState) {
    CharacterDTO nonPlayableCharacter = CharacterDTO.builder().state(illegalState).build();
    drools.insert(nonPlayableCharacter);
    drools.insert(changeRequest);
    drools.getSession().getAgenda().getAgendaGroup("character-state-change").setFocus();
    drools.fireAllRules();
    drools.assertFactsCount(2);
    ChangeRequest change = drools.getObject(ChangeRequest.class);
    CharacterDTO characterDTO = drools.getObject(CharacterDTO.class);
    assertEquals(ChangeStatus.REJECTED, change.getStatus());
    assertEquals(ChangeStatusLabel.WRONG_CHARACTER_STATE, change.getStatusLabel());
    Assertions.assertEquals(illegalState, characterDTO.getState());
  }

  @Test
  @TestRules(expected = {"Character change - make character ready for play - to many BSPs"})
  void characterChangeMakeReadyForPlayTooManyBSPs() {
    CharacterDTO nonPlayableCharacter = CharacterDTO
        .builder()
        .state(CharacterState.INIT_COMPLETE)
        .baseSkillPoints(10)
        .build();
    drools.insert(nonPlayableCharacter);
    drools.insert(changeRequest);
    drools.getSession().getAgenda().getAgendaGroup("character-state-change").setFocus();
    drools.fireAllRules();
    drools.assertFactsCount(2);
    ChangeRequest change = drools.getObject(ChangeRequest.class);
    CharacterDTO characterDTO = drools.getObject(CharacterDTO.class);
    assertEquals(ChangeStatus.REJECTED, change.getStatus());
    assertEquals(ChangeStatusLabel.TOO_MANY_SKILLS_POINTS, change.getStatusLabel());
    Assertions.assertEquals(CharacterState.INIT_COMPLETE, characterDTO.getState());
  }

  @Test
  @TestRules(expected = {"Validate character state"})
  void characterChangeBuySkillWhenReadyForPlay() {

    ChangeRequest buySkillRequest = ChangeRequest
        .builder()
        .changeDescription("Buy another skill...")
        .changeType(ChangeType.NEW_SKILL)
        .modifier(10)
        .changeKey(SkillKey.builder().value("primary.weapon").build())
        .build();
    playableCharacter.setState(CharacterState.READY_TO_PLAY);

    drools.insert(playableCharacter);
    drools.insert(buySkillRequest);
    drools.getSession().getAgenda().getAgendaGroup("new-skill").setFocus();
    drools.fireAllRules();
    drools.assertFactsCount(2);
    ChangeRequest change = drools.getObject(ChangeRequest.class);
    CharacterDTO characterDTO = drools.getObject(CharacterDTO.class);
    assertEquals(ChangeStatus.REJECTED, change.getStatus());
    assertEquals(ChangeStatusLabel.WRONG_CHARACTER_STATE, change.getStatusLabel());
  }
}
