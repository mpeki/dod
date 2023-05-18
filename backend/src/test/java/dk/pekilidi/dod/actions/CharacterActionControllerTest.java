package dk.pekilidi.dod.actions;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import dk.pekilidi.dod.actions.model.ActionResult;
import dk.pekilidi.dod.actions.model.Difficulty;
import dk.pekilidi.dod.actions.model.SkillTrainingAction;
import dk.pekilidi.dod.actions.model.Type;
import dk.pekilidi.dod.character.CharacterService;
import dk.pekilidi.dod.character.model.AgeGroup;
import dk.pekilidi.dod.data.CharacterDTO;
import dk.pekilidi.dod.data.RaceDTO;
import dk.pekilidi.dod.data.SkillDTO;
import dk.pekilidi.dod.skill.SkillNotFoundException;
import dk.pekilidi.dod.skill.SkillService;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

@WebMvcTest(CharacterActionController.class)
@Tag("regression")
class CharacterActionControllerTest {

  CharacterDTO testBeing;
  SkillTrainingAction testAction;

  @Autowired
  private ObjectMapper jacksonObjectMapper;

  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private CharacterActionService actionService;
  @MockBean
  private CharacterService characterService;
  @MockBean
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
  void trainSkill() throws Exception {
    SkillDTO skill = SkillDTO.builder().build();
    testAction.setSkill(skill);
    SkillTrainingAction actionSuccess = testAction
        .toBuilder()
        .actionResult(ActionResult.SUCCESS)
        .resultDescription("test action success!")
        .skillKey("primary.weapon")
        .skill(skill)
        .build();
    //    actionSuccess.setActor(testBeing);
    given(skillService.findSkillByKey("primary.weapon")).willReturn(skill);
    given(characterService.findCharacterById("testid")).willReturn(testBeing);
    given(actionService.doAction(testAction)).willReturn(actionSuccess);
    ResultActions resultActions = mockMvc
        .perform(MockMvcRequestBuilders
            .post("/action/training/char/testid/skill/primary.weapon")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jacksonObjectMapper.writeValueAsString(actionSuccess)))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isOk());
    jacksonObjectMapper
        .createParser(resultActions.andReturn().getResponse().getContentAsString())
        .readValueAs(SkillTrainingAction.class);
    Assert.assertEquals(ActionResult.SUCCESS, actionSuccess.getActionResult());
    Assert.assertEquals("test action success!", actionSuccess.getResultDescription());
  }

  @Test
  void trainNonSkill() throws Exception {
    given(skillService.findSkillByKey(anyString())).willThrow(new SkillNotFoundException());
    mockMvc.perform(MockMvcRequestBuilders.post("/action/training/char/1/skill/no-a-skill")).andExpect(status().isNotFound());
  }
}