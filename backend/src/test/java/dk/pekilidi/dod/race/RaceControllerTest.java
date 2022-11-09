package dk.pekilidi.dod.race;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import dk.pekilidi.dod.character.CharacterController;
import dk.pekilidi.dod.character.CharacterNotFoundException;
import dk.pekilidi.dod.character.CharacterService;
import dk.pekilidi.dod.data.CharacterDTO;
import dk.pekilidi.dod.data.RaceDTO;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(RaceController.class)
@Tag("regression")
class RaceControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private RaceService raceService;

  @Test
  void getCharacterNotFound() throws Exception {
    given(raceService.getRaceByName(anyString())).willThrow(new RaceNotFoundException());
    mockMvc.perform(MockMvcRequestBuilders.get("/race/name/bogorm")).andExpect(status().isNotFound());
  }


}