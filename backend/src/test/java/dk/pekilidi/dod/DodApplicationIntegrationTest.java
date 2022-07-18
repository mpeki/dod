package dk.pekilidi.dod;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import dk.pekilidi.dod.character.data.CharacterDTO;
import dk.pekilidi.dod.character.model.DODCharacter;

import dk.pekilidi.dod.util.objects.CharacterMapper;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class DodApplicationIntegrationTest {

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  void testGetNamedCharacter() throws Exception {

    //arrange
    //act
    ResponseEntity<Object[]> response = restTemplate.getForEntity("/char/name/{name}", Object[].class,"vokan fagerhård");
    Object[] responseBody = response.getBody();
    //assert
    assert responseBody != null;
    assert responseBody.length != 0;
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    List result = Arrays.stream(responseBody)
        .map(object -> mapper.convertValue(object, CharacterDTO.class))
        .collect(Collectors.toList());
    assert result.size() != 0;
    CharacterDTO character = (CharacterDTO)result.get(0);
    assertThat(character.getName()).isEqualTo("vokan fagerhård");
    assertThat(character.getRace().getName()).isEqualTo("human");
  }
}
