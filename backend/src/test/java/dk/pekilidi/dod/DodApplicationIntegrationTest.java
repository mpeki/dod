package dk.pekilidi.dod;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import dk.pekilidi.dod.data.CharacterDTO;
import org.h2.tools.Server;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@Tag("integration")
class DodApplicationIntegrationTest {

  @Autowired
  private TestRestTemplate restTemplate;

  static private Server webServer;
  static private Server tcpServer;
/*  @BeforeAll
  static public void initTest() throws SQLException {
    webServer = Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8082")
        .start();
    tcpServer = Server.createTcpServer("-tcpPort", "9092", "-tcpAllowOthers").start();
  }

  @AfterAll
  static public void cleanup() {
    webServer.stop();
    tcpServer.stop();
  }*/

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
    //    List result = Arrays.stream(responseBody)
    //        .map(object -> mapper.convertValue(object, CharacterDTO.class))
    //        .collect(Collectors.toList());
    //    assert result.size() != 0;
    //    CharacterDTO character = (CharacterDTO)result.get(0);
    //    assertThat(character.getName()).isEqualTo("vokan fagerhård");
    //    assertThat(character.getRace().getName()).isEqualTo("human");
  }
}
