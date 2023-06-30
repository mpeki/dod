package dk.pekilidi.dod;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import dk.pekilidi.dod.character.model.AgeGroup;
import dk.pekilidi.dod.character.model.BaseTraitName;
import dk.pekilidi.dod.character.model.CharacterState;
import dk.pekilidi.dod.data.CharacterDTO;
import dk.pekilidi.dod.data.RaceDTO;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.h2.tools.Server;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@Tag("integration")
class DodApplicationIntegrationTest {

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  void clearCacheEnpointShouldReturnOk() throws Exception {
    //arrange
    //act
    ResponseEntity<Void> response = restTemplate.exchange("/caches/clear", HttpMethod.DELETE, HttpEntity.EMPTY, Void.class);
    //assert
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
  }

  @Test
  void testCreateCharacter() throws Exception {
    //arrange
    CharacterDTO newCharacter = CharacterDTO.builder()
        .race(RaceDTO.builder().name("human").build())
        .name("Hagrun Brosson")
        .hero(true)
        .build();
    //act
    ResponseEntity<CharacterDTO> response = restTemplate.postForEntity(
        "/char", newCharacter, CharacterDTO.class);

    CharacterDTO createdChar = response.getBody();
    //assert
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
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
  void testGetNamedCharacter() throws Exception {

    //arrange
    //act
    ResponseEntity<CharacterDTO[]> response = restTemplate.getForEntity(
        "/char/name/{name}", CharacterDTO[].class, "vokan fagerhård");
    CharacterDTO[] responseBody = response.getBody();
    //assert
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assert responseBody != null;
    assert responseBody.length != 0;

    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        List<CharacterDTO> result = Arrays.stream(responseBody)
            .map(object -> mapper.convertValue(object, CharacterDTO.class))
            .toList();
        assert result.size() != 0;
        CharacterDTO character = result.get(0);
        assertThat(character.getName()).isEqualTo("vokan fagerhård");
        assertThat(character.getRace().getName()).isEqualTo("human");
  }
/*
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    assert result.size() != 0;
    CharacterDTO character = result.get(0);
*/

}
