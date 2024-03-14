package dk.dodgame;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dk.dodgame.data.CharacterDTO;
import dk.dodgame.data.RaceDTO;
import dk.dodgame.domain.character.model.AgeGroup;
import dk.dodgame.domain.character.model.BaseTraitName;
import dk.dodgame.domain.character.model.CharacterState;
import java.util.List;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@Tag("integration")
class DodApplicationIntegrationTest extends BaseControllerTest {

  @Autowired
  private ObjectMapper jacksonObjectMapper;

  @Test
  @WithMockUser(username = "system", password = "system", roles = {"system"})
  void clearCacheEnpointShouldReturnOk() throws Exception {
    mockMvc.perform(delete("/caches/clear")).andExpect(status().isOk());
  }

  @Test
  @WithMockUser(username = "player", password = "player", roles = {"player"})
  void testCreateCharacter() throws Exception {
    //arrange
    CharacterDTO newCharacter = CharacterDTO
        .builder()
        .race(RaceDTO.builder().name("human").build())
        .name("Hagrun Brosson")
        .hero(true)
        .build();

    MvcResult result = mockMvc
        .perform(MockMvcRequestBuilders
            .post("/api/char")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jacksonObjectMapper.writeValueAsString(newCharacter)))
        .andExpect(status().isOk())
        .andReturn();

    CharacterDTO createdChar = jacksonObjectMapper.readValue(
        result.getResponse().getContentAsString(), CharacterDTO.class);

    assert createdChar != null;
    assertThat(createdChar.getId()).isNotNull();
    assertThat(createdChar.getName()).isEqualTo("Hagrun Brosson");
    assertThat(createdChar.getRace().getName()).isEqualTo("human");
    assertThat(createdChar.isHero()).isTrue();
    assertThat(createdChar.getAgeGroup()).isEqualTo(AgeGroup.MATURE);
    assertThat(createdChar.getBaseTraits()).containsKey(BaseTraitName.CHARISMA);
    assertThat(createdChar.getBaseTraits()).containsKey(BaseTraitName.CONSTITUTION);
    assertThat(createdChar.getBaseTraits()).containsKey(BaseTraitName.PSYCHE);
    assertThat(createdChar.getHeroPoints()).isZero();
    assertThat(createdChar.getBaseSkillPoints()).isEqualTo(320);
    assertThat(createdChar.getState()).isEqualTo(CharacterState.INIT_COMPLETE);
  }

  @Test
  @WithMockUser(username = "player", password = "player", roles = {"player"})
  void testGetNamedCharacter() throws Exception {

    MvcResult result = mockMvc
        .perform(MockMvcRequestBuilders.get("/api/char/name/Vokan").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andReturn();

    String content = result.getResponse().getContentAsString();
    List<CharacterDTO> characters = new ObjectMapper().readValue(content, new TypeReference<List<CharacterDTO>>() {});

    assert characters != null;
    assert !characters.isEmpty();

    CharacterDTO character = characters.get(0);
    assertThat(character.getName()).isEqualTo("Vokan");
    assertThat(character.getRace().getName()).isEqualTo("human");
  }
}
