package dk.dodgame.domain.race;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import dk.dodgame.data.RaceDTO;
import dk.dodgame.domain.race.RaceNotFoundException;
import dk.dodgame.domain.race.RaceService;
import dk.dodgame.BaseControllerTest;
import java.util.Arrays;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@Tag("regression")
class RaceControllerTest extends BaseControllerTest {

  @MockBean
  private RaceService raceService;

  @Test
  @WithMockUser(username = "player", password = "player", roles = {"player"})
  void getCharacterNotFound() throws Exception {
    given(raceService.getRaceByName(anyString())).willThrow(new RaceNotFoundException());
    mockMvc.perform(MockMvcRequestBuilders.get("/api/race/name/bogorm")).andExpect(status().isNotFound());
  }

  @Test
  @WithMockUser(username = "player", password = "player", roles = {"player"})
  void getFetchRaces() throws Exception {
    given(raceService.fetchRaces()).willReturn(Arrays.asList(RaceDTO.builder().name("human").build()));
    mockMvc.perform(MockMvcRequestBuilders.get("/api/race")).andExpect(status().isOk());
  }
}