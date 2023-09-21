package dk.pekilidi.dod.character;

import static dk.pekilidi.utils.BaseTestUtil.TEST_OWNER;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import dk.pekilidi.dod.BaseControllerTest;
import dk.pekilidi.dod.character.model.AgeGroup;
import dk.pekilidi.dod.data.CharacterDTO;
import dk.pekilidi.dod.data.RaceDTO;
import dk.pekilidi.dod.race.RaceNotFoundException;
import dk.pekilidi.utils.RandomObjectFiller;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

@Tag("regression")
class DODCharacterControllerTest extends BaseControllerTest {

  CharacterDTO resultBeing;
  CharacterDTO testBeing;
  @Autowired
  private ObjectMapper jacksonObjectMapper;
  @MockBean
  private CharacterService characterService;

  @BeforeEach
  void setup() {
    resultBeing = new RandomObjectFiller().createAndFill(CharacterDTO.class);
    testBeing = CharacterDTO
        .builder()
        .name("hans")
        .race(RaceDTO.builder().name("tiefling").build())
        .ageGroup(AgeGroup.MATURE)
        .build();
    resultBeing.setName(testBeing.getName());
    resultBeing.setAgeGroup(AgeGroup.MATURE);
    resultBeing.setRace(RaceDTO.builder().name("tiefling").build());
  }

  @Test
  @WithMockUser(username = TEST_OWNER, password = "player", roles = {"player"})
  void getCharacterShouldReturnChar() throws Exception {
    given(characterService.findCharacterByIdAndOwner(anyString(), anyString())).willReturn(testBeing);
    mockMvc
        .perform(MockMvcRequestBuilders.get("/api/char/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("name").value("hans"))
        .andExpect(jsonPath("race.name").value("tiefling"));
  }

  @Test
  @WithMockUser(username = "player", password = "player", roles = {"player"})
  void deletedCharacterShouldNotBeFound() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.delete("/api/char/1")).andExpect(status().isOk());
  }

  @Test
  @WithMockUser(username = TEST_OWNER, password = "player", roles = {"player"})
  void fetchAllCharactersShouldFetchList() throws Exception {
    List<CharacterDTO> resultList = Arrays.asList(testBeing);
    given(characterService.fetchAllCharactersByOwner(TEST_OWNER)).willReturn(resultList);
    mockMvc
        .perform(MockMvcRequestBuilders.get("/api/char"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].name").value("hans"))
        .andExpect(jsonPath("$[0].race.name").value("tiefling"));
  }

  @Test
  @WithMockUser(username = "player", password = "player", roles = {"player"})
  void getCharacterNotFound() throws Exception {
    given(characterService.getCharactersByName(anyString())).willThrow(new CharacterNotFoundException());
    mockMvc.perform(MockMvcRequestBuilders.get("/api/char/name/kyron")).andExpect(status().isNotFound());
  }

  @Test
  @WithMockUser(username = TEST_OWNER, password = "player", roles = {"player"})
  void postCharacterShouldReturnChar() throws Exception {

    given(characterService.createCharacter(testBeing, TEST_OWNER)).willReturn(resultBeing);
    mockMvc
        .perform(MockMvcRequestBuilders
            .post("/api/char")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jacksonObjectMapper.writeValueAsString(testBeing)))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("name").value("hans"))
        .andExpect(jsonPath("ageGroup").value("MATURE"))
        .andExpect(jsonPath("race.name").value("tiefling"));
  }

  @Test
  @WithMockUser(username = TEST_OWNER, password = "player", roles = {"player"})
  void postCharacterWithNonRace() throws Exception {

    testBeing.setRace(RaceDTO.builder().name("Bogorm").build());
    given(characterService.createCharacter(testBeing, TEST_OWNER)).willThrow(new RaceNotFoundException());
    mockMvc
        .perform(MockMvcRequestBuilders
            .post("/api/char")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jacksonObjectMapper.writeValueAsString(testBeing)))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isNotFound());
  }

  @Test
  @WithMockUser(username = TEST_OWNER, password = "player", roles = {"player"})
  void postCharacterMaxExceeded() throws Exception {

    testBeing.setRace(RaceDTO.builder().name("Bogorm").build());
    given(characterService.createCharacter(testBeing, TEST_OWNER)).willThrow(new MaxCharactersReachedException());
    mockMvc
        .perform(MockMvcRequestBuilders
            .post("/api/char")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jacksonObjectMapper.writeValueAsString(testBeing)))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().is4xxClientError());
  }

  @Test
  @WithMockUser(username = TEST_OWNER, password = "system", roles = {"system"})
  void postBulkCreateShouldReturnArrayOfIDs() throws Exception {
    given(characterService.createCharacters(3, "human", TEST_OWNER)).willReturn(Arrays.asList("123", "124", "125"));
    mockMvc
        .perform(MockMvcRequestBuilders.post("/api/char/bulk/create/3").contentType(MediaType.APPLICATION_JSON))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0]").value("123"))
        .andExpect(jsonPath("$[1]").value("124"))
        .andExpect(jsonPath("$[2]").value("125"));
  }
}
