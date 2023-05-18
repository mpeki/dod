package dk.pekilidi.dod.skill;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import dk.pekilidi.dod.character.CharacterNotFoundException;
import dk.pekilidi.dod.character.model.BaseTraitName;
import dk.pekilidi.dod.data.SkillDTO;
import dk.pekilidi.dod.skill.model.Group;
import dk.pekilidi.utils.RandomObjectFiller;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

@WebMvcTest(SkillController.class)
@Tag("regression")
class SkillControllerTest {

  RandomObjectFiller objFill = new RandomObjectFiller();
  SkillDTO testSkill1;
  SkillDTO testSkill2;
  SkillDTO testSkill3;
  @Autowired
  private ObjectMapper jacksonObjectMapper;
  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private SkillService skillService;

  @BeforeEach
  void setup() throws Exception {
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
            .get("/skill?group=COMBAT")
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
            .get("/skill?baseChance=INTELLIGENCE")
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
            .get("/skill?group=COMBAT&baseChance=INTELLIGENCE")
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
            .get("/skill?group=&baseChance=")
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
            .get("/skill")
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
    mockMvc.perform(MockMvcRequestBuilders.get("/skill/key/no-a-skill")).andExpect(status().isNotFound());
  }
}
