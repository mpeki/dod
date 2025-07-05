package dk.dodgame.domain.race;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import dk.dodgame.BaseControllerTest;
import dk.dodgame.data.RaceDTO;

@Tag("regression")
class RaceControllerTest extends BaseControllerTest {

  @MockitoBean
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