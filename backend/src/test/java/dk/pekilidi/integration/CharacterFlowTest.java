package dk.pekilidi.integration;

import static dk.pekilidi.dod.character.model.BaseTraitName.STRENGTH;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import dk.pekilidi.dod.character.model.CharacterState;
import dk.pekilidi.dod.data.CharacterDTO;
import java.io.File;
import java.time.Duration;
import lombok.extern.slf4j.Slf4j;
import org.junit.ClassRule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.DockerComposeContainer.RemoveImages;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.containers.wait.strategy.WaitStrategy;

@Tag("integration")
@Slf4j
public class CharacterFlowTest {

  public static final String REQUEST_PROTOCOL = "http://";
  private static final String API_SERVICE_NAME = "api_1";
  private static final Integer API_PORT = 8090;
  public static final String API_SERVICE_PATH = "/dodgame/api";
  public static final String SEC_SERVICE_NAME = "security_1";
  private static final Integer SEC_PORT = 8181;
  public static final String AUTH_TOKEN_PATH = "/realms/dodgame/protocol/openid-connect/token";
  public static final String DB_SERVICE_NAME = "db_1";
  private static final Integer DATABASE_PORT = 3306;

  private static final WaitStrategy waitStrategy = Wait.forHealthcheck().withStartupTimeout(Duration.ofMinutes(5));

  @ClassRule
  public static DockerComposeContainer<?> compose = new DockerComposeContainer<>(new File("../docker-compose.yml"))
      .withPull(false)
      .withStartupTimeout(java.time.Duration.ofMinutes(15))
      .withExposedService(DB_SERVICE_NAME, DATABASE_PORT)
      .withExposedService(SEC_SERVICE_NAME, SEC_PORT, waitStrategy)
      .withExposedService(API_SERVICE_NAME, API_PORT)
      .withLogConsumer(DB_SERVICE_NAME, new Slf4jLogConsumer(log))
      .withLogConsumer(API_SERVICE_NAME, new Slf4jLogConsumer(log))
      .withLogConsumer(SEC_SERVICE_NAME, new Slf4jLogConsumer(log));

  static FlowTestHelper flowHelper;

  @BeforeAll
  public static void startup() {

    compose.start();

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    String serviceUrl = REQUEST_PROTOCOL + compose.getServiceHost(API_SERVICE_NAME, API_PORT) + ":" + compose.getServicePort(
        API_SERVICE_NAME, API_PORT) + API_SERVICE_PATH;

    String authUrl = REQUEST_PROTOCOL + compose.getServiceHost(SEC_SERVICE_NAME, SEC_PORT) + ":" + compose.getServicePort(
        SEC_SERVICE_NAME, SEC_PORT) + AUTH_TOKEN_PATH;

    flowHelper = new FlowTestHelper(authUrl, serviceUrl, headers);
  }

  @Test
  void simpleCharacterFlow() throws InterruptedException {

    //Fetch and count characters
    int initalCharCount = flowHelper.fetchAllCharacters().length;

    //Create a character
    CharacterDTO createdChar = flowHelper.createNewCharacter("Dendrik", true);
    assertNotNull(createdChar.getId());

    //Fetch and count characters
    int newCharCount = flowHelper.fetchAllCharacters().length;
    assertNotEquals(initalCharCount, newCharCount);
    assertEquals(initalCharCount + 1, newCharCount);

    //Fetch the character created
    CharacterDTO fetchedChar = flowHelper.getCharById(createdChar.getId());
    createdChar.setWeightCarried(fetchedChar.getWeightCarried());
    assertEquals(createdChar, fetchedChar);

    //update the character - increase number of hero points
    CharacterDTO changedChar = flowHelper.buyHeroPoints(createdChar.getId(), 4);
    fetchedChar = flowHelper.getCharById(createdChar.getId());
    assertEquals(changedChar, fetchedChar);
    assertNotEquals(createdChar, fetchedChar);
    assertEquals(4, fetchedChar.getHeroPoints());

    //Modify the character - buy more strength
    changedChar = flowHelper.buyBaseTraitIncrease(createdChar.getId(), STRENGTH, 1);
    fetchedChar = flowHelper.getCharById(createdChar.getId());
    assertEquals(changedChar, fetchedChar);
    assertNotEquals(createdChar, fetchedChar);
    assertEquals(2, fetchedChar.getHeroPoints());
    assertEquals(createdChar.getBaseTraitValue(STRENGTH) + 1, fetchedChar.getBaseTraitValue(STRENGTH));

    //Fetch available skills - and buy some
    int numSkillsBought = flowHelper.buySkills(createdChar.getId());

    //Check skills of fetched character
    fetchedChar = flowHelper.getCharById(createdChar.getId());
    assertEquals(numSkillsBought, fetchedChar.getSkills().size());
    log.debug("Base skill points: {}", fetchedChar.getBaseSkillPoints());

    if(fetchedChar.getBaseSkillPoints() < 5) {
      flowHelper.makeCharacterReadyToPlay(fetchedChar.getId());
      fetchedChar = flowHelper.getCharById(createdChar.getId());
      assertEquals(CharacterState.READY_TO_PLAY, fetchedChar.getState());
    }

    //Check number of characters
    CharacterDTO[] characters = flowHelper.fetchAllCharacters();
    newCharCount = characters.length;
    assertNotEquals(initalCharCount, newCharCount);
    assertEquals(initalCharCount + 1, newCharCount);

    //Delete the character - and assert it was deleted
    flowHelper.deleteCharacter(fetchedChar.getId());

    newCharCount = flowHelper.fetchAllCharacters().length;
    assertEquals(initalCharCount, newCharCount);
  }

  @Test
  void characterCreationMulti() throws InterruptedException {
    int initalCharCount = flowHelper.fetchAllCharacters().length;
    int numChars = 10;
    for (int i = 0; i < numChars; i++) {
      CharacterDTO createdChar = flowHelper.createNewCharacter("tester_" + i, true);
      assertNotNull(createdChar.getId());
      //Fetch available skills - and buy some
      int numSkillsBought = flowHelper.buySkills(createdChar.getId());
      //Check skills of fetched character
      CharacterDTO fetchedChar = flowHelper.getCharById(createdChar.getId());
      assertEquals(numSkillsBought, fetchedChar.getSkills().size());
    }
    int newCharCount = flowHelper.fetchAllCharacters().length;
    assertNotEquals(initalCharCount, newCharCount);
    assertEquals(initalCharCount + numChars, newCharCount);
    assertThrows(HttpClientErrorException.class, () -> flowHelper.createNewCharacter("tester_11", true));
  }
}
