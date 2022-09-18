package dk.pekilidi.dod.character;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import dk.pekilidi.dod.character.model.AgeGroup;
import dk.pekilidi.dod.data.CharacterDTO;
import dk.pekilidi.dod.data.RaceDTO;
import dk.pekilidi.utils.RandomObjectFiller;
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

@WebMvcTest(CharacterController.class)
@Tag("regression")
class DODCharacterControllerTest {

  @Autowired
  private ObjectMapper jacksonObjectMapper;

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private CharacterService characterService;

  CharacterDTO resultBeing;
  CharacterDTO testBeing;

  @BeforeEach
  void setup() throws Exception {
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
  void getCharacterShouldReturnChar() throws Exception {
    given(characterService.findCharacterById(anyLong())).willReturn(testBeing);
    mockMvc
        .perform(MockMvcRequestBuilders.get("/char/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("name").value("hans"))
        .andExpect(jsonPath("race.name").value("tiefling"));
  }

  @Test
  void getCharacterNotFound() throws Exception {
    given(characterService.getCharactersByName(anyString())).willThrow(new CharacterNotFoundException());
    mockMvc.perform(MockMvcRequestBuilders.get("/char/name/kyron")).andExpect(status().isNotFound());
  }

  @Test
  void postCharacterShouldReturnChar() throws Exception {

    given(characterService.createCharacter(testBeing)).willReturn(resultBeing);
    mockMvc
        .perform(MockMvcRequestBuilders
            .post("/char")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jacksonObjectMapper.writeValueAsString(testBeing)))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("name").value("hans"))
        .andExpect(jsonPath("ageGroup").value("MATURE"))
        .andExpect(jsonPath("race.name").value("tiefling"));
  }
}
