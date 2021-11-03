package dk.pekilidi.dod.character;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import dk.pekilidi.dod.character.model.Being;
import dk.pekilidi.dod.character.model.Race;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(DODCharacterController.class)
public class DODCharacterControllerTest {

  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private DODCharacterService characterService;

  @Test
  public void getCharacterShouldReturnChar() throws Exception {

    given(characterService.getCharacterByName(anyString())).willReturn(new Being(0L,"kyron",new Race(0L,"tiefling")));

    mockMvc.perform(MockMvcRequestBuilders.get("/char/kyron"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("name").value("kyron"))
        .andExpect(jsonPath("race.name").value("tiefling"));
  }

  @Test
  public void getCharacterNotFound() throws Exception {
    given(characterService.getCharacterByName(anyString())).willThrow(new CharacterNotFoundException());
    mockMvc.perform(MockMvcRequestBuilders.get("/char/kyron"))
        .andExpect(status().isNotFound());

  }

}
