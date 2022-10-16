package dk.pekilidi.dod;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import dk.pekilidi.dod.character.model.Being;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class DodApplicationIntegrationTest {

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  public void testGetNamedCharacter() throws Exception {

    //arrange
    //act
    ResponseEntity<Being> response = restTemplate.getForEntity("/char/{name}", Being.class,"kyron");
    Being character = response.getBody();
    //assert
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(character.getName()).isEqualTo("kyron");
    assertThat(character.getRace().getName()).isEqualTo("tiefling");
  }
}
