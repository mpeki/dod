package dk.pekilidi.integration;

import static dk.pekilidi.dod.character.model.BaseTraitName.STRENGTH;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import dk.pekilidi.dod.changerequest.model.ChangeRequest;
import dk.pekilidi.dod.changerequest.model.ChangeStatus;
import dk.pekilidi.dod.changerequest.model.ChangeStatusLabel;
import dk.pekilidi.dod.changerequest.model.ChangeType;
import dk.pekilidi.dod.character.model.AgeGroup;
import dk.pekilidi.dod.character.model.CharacterState;
import dk.pekilidi.dod.data.CharacterDTO;
import dk.pekilidi.dod.data.RaceDTO;
import java.io.File;
import org.junit.ClassRule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;

@Tag("integration")
public class CharacterFlowTest {

  private static HttpHeaders headers;
  private static String serviceUrl;
  private static String createCharacterUrl;
  private static String getCharacterUrl;
  private static String changeCharUrl;
  private static final Integer BACKEND_PORT = 8090;
  private static final Integer DATABASE_PORT = 3306;

  @ClassRule
  public static DockerComposeContainer compose = new DockerComposeContainer(new File("../docker-compose.yml"))
      .withPull(false)
      .withExposedService("db_1", DATABASE_PORT)
      .withExposedService("backend_1", BACKEND_PORT)
      .waitingFor("backend_1", Wait.forHealthcheck());

  @BeforeAll
  public static void startup() {
    compose.start();

    headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    serviceUrl = "http://" + compose.getServiceHost("backend_1", BACKEND_PORT) + ":" + compose.getServicePort(
        "backend_1", BACKEND_PORT);
    createCharacterUrl = serviceUrl + "/char";
  }

  @Test
  void simpleCharacterFlow() {

    //Create a character
    CharacterDTO character = CharacterDTO
        .builder()
        .name("Hendrik")
        .race(RaceDTO.builder().name("human").build())
        .ageGroup(AgeGroup.MATURE)
        .hero(true)
        .build();

    RestTemplate restTemplate = new RestTemplate();

    HttpEntity<CharacterDTO> request = new HttpEntity<>(character, headers);
    ResponseEntity<CharacterDTO> createdResponse = restTemplate.postForEntity(
        createCharacterUrl, request, CharacterDTO.class);
    assertEquals(createdResponse.getStatusCode(), HttpStatus.OK);
    assertEquals(CharacterState.INIT_COMPLETE, createdResponse.getBody().getState());

    CharacterDTO createdChar = createdResponse.getBody();
    assertNotNull(createdChar.getId());

    //
    getCharacterUrl = serviceUrl + "/char/" + createdChar.getId();

    ResponseEntity<CharacterDTO> getResponse = restTemplate.getForEntity(getCharacterUrl, CharacterDTO.class);
    assertEquals(getResponse.getStatusCode(), HttpStatus.OK);
    CharacterDTO fetchedChar = getResponse.getBody();
    assertEquals(createdChar, fetchedChar);

    changeCharUrl = serviceUrl + "/change/char/" + createdChar.getId();

    ChangeRequest increaseHeroPoints = ChangeRequest
        .builder()
        .changeDescription("Increase hero points")
        .changeType(ChangeType.HERO_POINTS)
        .modifier(4)
        .build();

    HttpEntity<ChangeRequest> changeRequest = new HttpEntity<>(increaseHeroPoints, headers);
    ResponseEntity<ChangeRequest> changeResponse = restTemplate.postForEntity(
        changeCharUrl, changeRequest, ChangeRequest.class);
    assertEquals(changeResponse.getStatusCode(), HttpStatus.OK);
    assertEquals(ChangeStatus.APPROVED, changeResponse.getBody().getStatus());
    assertEquals(ChangeStatusLabel.OK_HERO_POINTS_INCREASE, changeResponse.getBody().getStatusLabel());

    getResponse = restTemplate.getForEntity(getCharacterUrl, CharacterDTO.class);
    assertEquals(getResponse.getStatusCode(), HttpStatus.OK);
    fetchedChar = getResponse.getBody();
    assertNotEquals(createdChar, fetchedChar);
    assertEquals(4, fetchedChar.getHeroPoints());

    ChangeRequest increaseBaseTrait = ChangeRequest
        .builder()
        .changeDescription("Increase base trait STRENGTH")
        .changeType(ChangeType.BASE_TRAIT)
        .modifier(1)
        .changeKey(STRENGTH)
        .build();

    changeRequest = new HttpEntity<>(increaseBaseTrait, headers);
    changeResponse = restTemplate.postForEntity(changeCharUrl, changeRequest, ChangeRequest.class);
    assertEquals(changeResponse.getStatusCode(), HttpStatus.OK);
    assertEquals(ChangeStatus.APPROVED, changeResponse.getBody().getStatus());
    assertEquals(ChangeStatusLabel.OK_BASE_TRAIT_MODIFICATION, changeResponse.getBody().getStatusLabel());

    getResponse = restTemplate.getForEntity(getCharacterUrl, CharacterDTO.class);
    assertEquals(getResponse.getStatusCode(), HttpStatus.OK);
    fetchedChar = getResponse.getBody();
    assertNotEquals(createdChar, fetchedChar);
    assertEquals(2, fetchedChar.getHeroPoints());
    assertEquals(createdChar.getBaseTraitValue(STRENGTH) + 1, fetchedChar.getBaseTraitValue(STRENGTH));
    System.out.println(fetchedChar);
  }
}
