package dk.pekilidi.dod.changerequest;

import static dk.pekilidi.dod.character.model.BaseTraitName.STRENGTH;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import dk.pekilidi.dod.BaseControllerTest;
import dk.pekilidi.dod.changerequest.model.ChangeRequest;
import dk.pekilidi.dod.changerequest.model.ChangeType;
import dk.pekilidi.dod.character.CharacterNotFoundException;
import dk.pekilidi.dod.character.CharacterService;
import dk.pekilidi.dod.character.model.AgeGroup;
import dk.pekilidi.dod.data.CharacterDTO;
import dk.pekilidi.dod.data.RaceDTO;
import dk.pekilidi.dod.race.RaceNotFoundException;
import dk.pekilidi.dod.security.WebSecurityConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@Tag("regression")
class ChangeRequestControllerTest extends BaseControllerTest {
  @Autowired
  private ObjectMapper jacksonObjectMapper;
  @MockBean
  private ChangeRequestService changeRequestService;
  ChangeRequest change = null;
  CharacterDTO testBeing;
  @BeforeEach
  void setup() {

    testBeing = CharacterDTO
        .builder()
        .name("hans")
        .race(RaceDTO.builder().name("tiefling").build())
        .ageGroup(AgeGroup.MATURE)
        .build();

    change = ChangeRequest
        .builder()
        .changeType(ChangeType.BASE_TRAIT)
        .changeKey(STRENGTH)
        .objectBeforeChange(testBeing)
        .changeDescription("Add 1 point to the STRENGTH BASE_TRAIT")
        .modifier(1)
        .build();
  }

  @Test
  @WithMockUser(username = "player", password = "player", roles = {WebSecurityConfig.PLAYER})
  void buyBasetraitIncrease() throws Exception {

    given(changeRequestService.submitChangeRequest("123", change)).willReturn(change);

    mockMvc
        .perform(MockMvcRequestBuilders
            .post("/api/change/char/2")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jacksonObjectMapper.writeValueAsString(change)))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isOk());
  }

  @Test
  @WithMockUser(username = "player", password = "player", roles = {WebSecurityConfig.PLAYER})
  void getCharacterNotFound() throws Exception {
    given(changeRequestService.submitChangeRequest(anyString(), any())).willThrow(new CharacterNotFoundException());
    mockMvc.perform(MockMvcRequestBuilders
        .post("/api/change/char/123")
        .contentType(MediaType.APPLICATION_JSON)
        .content(jacksonObjectMapper.writeValueAsString(change)))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isNotFound());
  }

  @Test
  @WithMockUser(username = "player", password = "player", roles = {WebSecurityConfig.PLAYER})
  void getRaceNotFound() throws Exception {
    given(changeRequestService.submitChangeRequest(anyString(), any())).willThrow(new RaceNotFoundException());
    mockMvc.perform(MockMvcRequestBuilders
            .post("/api/change/char/123")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jacksonObjectMapper.writeValueAsString(change)))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isNotFound());
  }

}
