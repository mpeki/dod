package dk.pekilidi.dod;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import dk.pekilidi.dod.character.model.DODCharacter;

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
    ResponseEntity<DODCharacter> response = restTemplate.getForEntity("/char/{name}", DODCharacter.class,"vokan fagerhård");
    DODCharacter character = response.getBody();
    //assert
    assert character != null;
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(character.getName()).isEqualTo("vokan fagerhård");
    assertThat(character.getRace().getName()).isEqualTo("human");
  }
}
