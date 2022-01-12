package dk.pekilidi.dod.character;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import dk.pekilidi.dod.character.data.BeingDTO;
import dk.pekilidi.dod.character.data.RaceDTO;
import dk.pekilidi.dod.character.model.Being;
import dk.pekilidi.dod.character.model.Race;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(DODCharacterController.class)
class DODCharacterControllerTest {

  @Autowired
  private ObjectMapper jacksonObjectMapper;

  @Autowired
  private MockMvc mockMvc;


  @MockBean
  private DODCharacterService characterService;

  @Test
  void getCharacterShouldReturnChar() throws Exception {

    given(characterService.getCharacterByName(anyString())).willReturn(new Being(0L,"kyron",new Race(0L,"tiefling")));

    mockMvc.perform(MockMvcRequestBuilders.get("/char/kyron"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("name").value("kyron"))
        .andExpect(jsonPath("race.name").value("tiefling"));
  }

  @Test
  void getCharacterNotFound() throws Exception {
    given(characterService.getCharacterByName(anyString())).willThrow(new CharacterNotFoundException());
    mockMvc.perform(MockMvcRequestBuilders.get("/char/kyron"))
        .andExpect(status().isNotFound());

  }

  @Test
  void postCharacterShouldReturnChar() throws Exception {

    BeingDTO being = new BeingDTO("hans",new RaceDTO("tiefling"));
    given(characterService.createCharacter(being)).willReturn(new Being(0L,"hans",new Race(0L,"tiefling")));
    mockMvc.perform(MockMvcRequestBuilders.post("/char")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jacksonObjectMapper.writeValueAsString(being)))
//              .andDo(MockMvcResultHandlers.print())
              .andExpect(status().isOk())
              .andExpect(jsonPath("name").value("hans"))
              .andExpect(jsonPath("race.name").value("tiefling"));
  }


}
