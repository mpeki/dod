package dk.dodgame.domain.skill;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.fasterxml.jackson.databind.ObjectMapper;

import dk.dodgame.BaseControllerTest;
import dk.dodgame.data.SkillDTO;
import dk.dodgame.domain.character.model.BaseTraitName;
import dk.dodgame.domain.skill.model.Group;
import dk.dodgame.util.RandomObjectFiller;

@Tag("regression")
class SkillControllerTest extends BaseControllerTest {

  RandomObjectFiller objFill = new RandomObjectFiller();
  SkillDTO testSkill1;
  SkillDTO testSkill2;
  SkillDTO testSkill3;
  @Autowired
  private ObjectMapper jacksonObjectMapper;

  @MockitoBean
  private SkillService skillService;

  @BeforeEach
  void setup() {
    testSkill1 = objFill.createAndFill(SkillDTO.class);
    testSkill2 = objFill.createAndFill(SkillDTO.class);
    testSkill3 = objFill.createAndFill(SkillDTO.class);
  }

  @Test
  void findSkillsByGroup() throws Exception {
    List<SkillDTO> resultList = new ArrayList<>();
    resultList.add(testSkill1);
    given(skillService.getSkillsByGroup(Group.COMBAT)).willReturn(resultList);
    mockMvc
        .perform(MockMvcRequestBuilders
            .get("/api/skill?group=COMBAT")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jacksonObjectMapper.writeValueAsString(resultList)))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0].group", is(testSkill1.getGroup().toString())))
        .andExpect(jsonPath("$[0].category", is(testSkill1.getCategory().toString())))
        .andExpect(jsonPath("$[0].traitName", is(testSkill1.getTraitName().toString())))
        .andExpect(jsonPath("$[0].baseChance", is(testSkill1.getBaseChance().toString())))
        .andExpect(jsonPath("$[0].price", is(testSkill1.getPrice())));
  }

  @Test
  void findSkillsByBaseChance() throws Exception {
    List<SkillDTO> resultList = new ArrayList<>();
    resultList.add(testSkill2);
    given(skillService.getSkillsByBaseChance(BaseTraitName.INTELLIGENCE)).willReturn(resultList);
    mockMvc
        .perform(MockMvcRequestBuilders
            .get("/api/skill?baseChance=INTELLIGENCE")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jacksonObjectMapper.writeValueAsString(resultList)))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0].group", is(testSkill2.getGroup().toString())))
        .andExpect(jsonPath("$[0].category", is(testSkill2.getCategory().toString())))
        .andExpect(jsonPath("$[0].traitName", is(testSkill2.getTraitName().toString())))
        .andExpect(jsonPath("$[0].baseChance", is(testSkill2.getBaseChance().toString())))
        .andExpect(jsonPath("$[0].price", is(testSkill2.getPrice())));
  }

  @Test
  void findSkillsByGroupAndBaseChance() throws Exception {
    List<SkillDTO> resultList = new ArrayList<>();
    resultList.add(testSkill2);
    given(skillService.getSkillsByGroupAndBaseChance(Group.COMBAT, BaseTraitName.INTELLIGENCE)).willReturn(resultList);
    mockMvc
        .perform(MockMvcRequestBuilders
            .get("/api/skill?group=COMBAT&baseChance=INTELLIGENCE")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jacksonObjectMapper.writeValueAsString(resultList)))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0].group", is(testSkill2.getGroup().toString())))
        .andExpect(jsonPath("$[0].category", is(testSkill2.getCategory().toString())))
        .andExpect(jsonPath("$[0].traitName", is(testSkill2.getTraitName().toString())))
        .andExpect(jsonPath("$[0].baseChance", is(testSkill2.getBaseChance().toString())))
        .andExpect(jsonPath("$[0].price", is(testSkill2.getPrice())));
  }

  @Test
  void findSkills() throws Exception {
    List<SkillDTO> resultList = new ArrayList<>();
    resultList.add(testSkill1);
    resultList.add(testSkill2);
    resultList.add(testSkill3);
    given(skillService.getSkills()).willReturn(resultList);
    given(skillService.getSkillsByGroupAndBaseChance(Group.COMBAT, BaseTraitName.INTELLIGENCE)).willReturn(resultList);
    mockMvc
        .perform(MockMvcRequestBuilders
            .get("/api/skill?group=&baseChance=")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jacksonObjectMapper.writeValueAsString(resultList)))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$", hasSize(3)))
        .andExpect(jsonPath("$[1].group", is(testSkill2.getGroup().toString())))
        .andExpect(jsonPath("$[1].category", is(testSkill2.getCategory().toString())))
        .andExpect(jsonPath("$[1].traitName", is(testSkill2.getTraitName().toString())))
        .andExpect(jsonPath("$[1].baseChance", is(testSkill2.getBaseChance().toString())))
        .andExpect(jsonPath("$[1].price", is(testSkill2.getPrice())));

    mockMvc
        .perform(MockMvcRequestBuilders
            .get("/api/skill")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jacksonObjectMapper.writeValueAsString(resultList)))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$", hasSize(3)))
        .andExpect(jsonPath("$[1].group", is(testSkill2.getGroup().toString())))
        .andExpect(jsonPath("$[1].category", is(testSkill2.getCategory().toString())))
        .andExpect(jsonPath("$[1].traitName", is(testSkill2.getTraitName().toString())))
        .andExpect(jsonPath("$[1].baseChance", is(testSkill2.getBaseChance().toString())))
        .andExpect(jsonPath("$[1].price", is(testSkill2.getPrice())));
  }

  @Test
  void getSkillNotFound() throws Exception {
    given(skillService.findSkillByKey(anyString())).willThrow(new SkillNotFoundException());
    mockMvc.perform(MockMvcRequestBuilders.get("/api/skill/key/no-a-skill")).andExpect(status().isNotFound());
  }
}
