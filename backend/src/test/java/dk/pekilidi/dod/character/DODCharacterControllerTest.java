package dk.pekilidi.dod.character;

import static dk.pekilidi.dod.character.BaseTraitName.STRENGTH;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import dk.pekilidi.dod.character.data.CharacterDTO;
import dk.pekilidi.dod.character.data.RaceDTO;
import dk.pekilidi.dod.rules.changes.ChangeRequest;
import dk.pekilidi.dod.rules.changes.ChangeType;
import dk.pekilidi.utils.RandomObjectFiller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

@WebMvcTest(DODCharacterController.class)
class DODCharacterControllerTest {

  @Autowired
  private ObjectMapper jacksonObjectMapper;

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private DODCharacterService characterService;

  CharacterDTO resultBeing;
  CharacterDTO testBeing;
  @BeforeEach
  void setup() throws Exception {
    resultBeing = new RandomObjectFiller().createAndFill(CharacterDTO.class);
    testBeing = CharacterDTO.builder().name("hans").race(RaceDTO.builder().name("tiefling").build()).ageGroup(AgeGroup.MATURE).build();
    resultBeing.setName(testBeing.getName());
    resultBeing.setAgeGroup(AgeGroup.MATURE);
    resultBeing.setRace(RaceDTO.builder().name("tiefling").build());
  }

  @Test
  void getCharacterShouldReturnChar() throws Exception {
    given(characterService.findCharacterById(anyLong())).willReturn(testBeing);
    mockMvc.perform(MockMvcRequestBuilders.get("/char/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("name").value("hans"))
        .andExpect(jsonPath("race.name").value("tiefling"));
  }

  @Test
  void getCharacterNotFound() throws Exception {
    given(characterService.getCharactersByName(anyString())).willThrow(new CharacterNotFoundException());
    mockMvc.perform(MockMvcRequestBuilders.get("/char/name/kyron"))
        .andExpect(status().isNotFound());

  }

  @Test
  void postCharacterShouldReturnChar() throws Exception {

    given(characterService.createCharacter(testBeing)).willReturn(resultBeing);
    mockMvc.perform(MockMvcRequestBuilders.post("/char")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jacksonObjectMapper.writeValueAsString(testBeing)))
              .andDo(MockMvcResultHandlers.print())
              .andExpect(status().isOk())
              .andExpect(jsonPath("name").value("hans"))
              .andExpect(jsonPath("ageGroup").value("MATURE"))
              .andExpect(jsonPath("race.name").value("tiefling"));
  }

  @Test
  void buyBasetraitIncrease() throws Exception {

    ChangeRequest change = ChangeRequest.builder()
        .changeType(ChangeType.BASE_TRAIT)
        .changeKey(STRENGTH)
        .objectBeforeChange(testBeing)
        .changeDescription("Add 1 point to the STRENGTH BASE_TRAIT")
        .modifier(1).build();
    if(change.equals(change)){
      System.out.println("here we are");
    }

//    given(characterService.findCharacterById()).
    given(characterService.increaseBasetrait(1L,change)).willReturn(change);

    mockMvc.perform(MockMvcRequestBuilders.post("/char/2/change")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jacksonObjectMapper.writeValueAsString(change)))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isOk());
//        .andExpect(jsonPath("name").value("hans"))
//        .andExpect(jsonPath("ageGroup").value("MATURE"))
//        .andExpect(jsonPath("race.name").value("tiefling"));
  }


}
