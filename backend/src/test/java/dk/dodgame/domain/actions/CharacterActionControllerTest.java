package dk.dodgame.domain.actions;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.fasterxml.jackson.databind.ObjectMapper;

import dk.dodgame.BaseControllerTest;
import dk.dodgame.data.CharacterDTO;
import dk.dodgame.data.RaceDTO;
import dk.dodgame.data.SkillDTO;
import dk.dodgame.domain.action.CharacterActionService;
import dk.dodgame.domain.action.model.*;
import dk.dodgame.domain.character.CharacterService;
import dk.dodgame.domain.character.model.AgeGroup;
import dk.dodgame.domain.skill.SkillNotFoundException;
import dk.dodgame.domain.skill.SkillService;

@Tag("regression")
class CharacterActionControllerTest extends BaseControllerTest {

  CharacterDTO testBeing;
  SkillTrainingAction testAction;

  @Autowired
  private ObjectMapper jacksonObjectMapper;
  @MockitoBean
  private CharacterActionService actionService;
  @MockitoBean
  private CharacterService characterService;
  @MockitoBean
  private SkillService skillService;

  @BeforeEach
  void setUp() {
    testBeing = CharacterDTO
        .builder()
        .id("testid")
        .name("hans")
        .race(RaceDTO.builder().name("human").build())
        .ageGroup(AgeGroup.MATURE)
        .build();

    testAction = SkillTrainingAction
        .builder()
        .actor(testBeing)
        .difficulty(Difficulty.NORMAL)
        .skillKey("primary.weapon")
        .type(Type.SKILL_TRAINING)
        .build();
  }

  @Test
  @WithMockUser(username = "player", password = "player", roles = {"player"})
  void trainSkill() throws Exception {
    SkillDTO skill = SkillDTO.builder().build();
    testAction.setSkill(skill);
    SkillTrainingAction actionSuccess = testAction
        .toBuilder()
        .actionResult(ActionResult.SUCCESS)
        .resultDescription(ActionResultDescription.builder().actionResult("test action success!").build())
        .skillKey("primary.weapon")
        .skill(skill)
        .build();
    //    actionSuccess.setActor(testBeing);
    given(skillService.findSkillByKey("primary.weapon")).willReturn(skill);
    given(characterService.findCharacterByIdAndOwner("testid", "player")).willReturn(testBeing);
    given(actionService.doAction(testAction)).willReturn(actionSuccess);
    ResultActions resultActions = mockMvc
        .perform(MockMvcRequestBuilders
            .post("/api/action/training/char/testid/skill/primary.weapon")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jacksonObjectMapper.writeValueAsString(actionSuccess)))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isOk());
    jacksonObjectMapper
        .createParser(resultActions.andReturn().getResponse().getContentAsString())
        .readValueAs(SkillTrainingAction.class);
    Assertions.assertEquals(ActionResult.SUCCESS, actionSuccess.getActionResult());
    Assertions.assertEquals("test action success!", actionSuccess.getResultDescription());
  }

  @Test
  @WithMockUser(username = "player", password = "player", roles = {"player"})
  void trainNonSkill() throws Exception {
    given(skillService.findSkillByKey(anyString())).willThrow(new SkillNotFoundException());
    mockMvc
        .perform(MockMvcRequestBuilders.post("/api/action/training/char/1/skill/no-a-skill"))
        .andExpect(status().isNotFound());
  }
}