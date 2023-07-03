package dk.pekilidi.dod.changerequest;

import static dk.pekilidi.dod.character.model.BaseTraitName.STRENGTH;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import dk.pekilidi.dod.changerequest.model.ChangeRequest;
import dk.pekilidi.dod.changerequest.model.ChangeType;
import dk.pekilidi.dod.character.CharacterNotFoundException;
import dk.pekilidi.dod.character.CharacterService;
import dk.pekilidi.dod.character.model.AgeGroup;
import dk.pekilidi.dod.data.CharacterDTO;
import dk.pekilidi.dod.data.RaceDTO;
import dk.pekilidi.dod.race.RaceNotFoundException;
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

@WebMvcTest(ChangeRequestController.class)
@Tag("regression")
class ChangeRequestControllerTest {
  @Autowired
  private ObjectMapper jacksonObjectMapper;
  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private ChangeRequestService changeRequestService;
  ChangeRequest change = null;
  CharacterDTO testBeing;
  @BeforeEach
  void setup() throws Exception {
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
  void buyBasetraitIncrease() throws Exception {

    given(changeRequestService.submitChangeRequest("123", change)).willReturn(change);

    mockMvc
        .perform(MockMvcRequestBuilders
            .post("/change/char/2")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jacksonObjectMapper.writeValueAsString(change)))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isOk());
  }

  @Test
  void getCharacterNotFound() throws Exception {
    given(changeRequestService.submitChangeRequest(anyString(), any())).willThrow(new CharacterNotFoundException());
    mockMvc.perform(MockMvcRequestBuilders
        .post("/change/char/123")
        .contentType(MediaType.APPLICATION_JSON)
        .content(jacksonObjectMapper.writeValueAsString(change)))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isNotFound());
  }

  @Test
  void getRaceNotFound() throws Exception {
    given(changeRequestService.submitChangeRequest(anyString(), any())).willThrow(new RaceNotFoundException());
    mockMvc.perform(MockMvcRequestBuilders
            .post("/change/char/123")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jacksonObjectMapper.writeValueAsString(change)))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isNotFound());
  }

}
