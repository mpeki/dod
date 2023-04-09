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
import dk.pekilidi.dod.character.model.BaseTraitName;
import dk.pekilidi.dod.character.model.CharacterState;
import dk.pekilidi.dod.data.CharacterDTO;
import dk.pekilidi.dod.data.RaceDTO;
import dk.pekilidi.dod.data.SkillDTO;
import dk.pekilidi.dod.skill.SkillService;
import dk.pekilidi.dod.skill.model.Category;
import java.io.File;
import org.junit.ClassRule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;

@Tag("integration")
public class CharacterFlowTest {
  private static final Integer BACKEND_PORT = 8090;
  private static final Integer DATABASE_PORT = 3306;
  static FlowTestHelper flowHelper;

  @ClassRule
  public static DockerComposeContainer compose = new DockerComposeContainer(new File("../docker-compose.yml"))
      .withPull(false)
      .withExposedService("db_1", DATABASE_PORT)
      .withExposedService("backend_1", BACKEND_PORT)
      .waitingFor("backend_1", Wait.forHealthcheck());

  @BeforeAll
  public static void startup() {
    compose.start();

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    String serviceUrl = "http://" + compose.getServiceHost("backend_1", BACKEND_PORT) + ":" + compose.getServicePort(
        "backend_1", BACKEND_PORT);
    flowHelper = new FlowTestHelper(serviceUrl, headers);
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
    assertEquals(initalCharCount+1, newCharCount);

    //Fetch the character created
    CharacterDTO fetchedChar = flowHelper.getCharById(createdChar.getId());
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

    //Check number of characters
    CharacterDTO[] characters = flowHelper.fetchAllCharacters();
    newCharCount = characters.length;
    assertNotEquals(initalCharCount, newCharCount);
    assertEquals(initalCharCount+1, newCharCount);


    //Delete the character - and assert it was deleted
    flowHelper.deleteCharacter(fetchedChar.getId());

    newCharCount = flowHelper.fetchAllCharacters().length;
    assertEquals(initalCharCount, newCharCount);

  }

  @Test
  void characterCreationMulti() throws InterruptedException {
    int initalCharCount = flowHelper.fetchAllCharacters().length;
    int numChars = 20;
    for (int i = 0; i < numChars; i++) {
      CharacterDTO createdChar = flowHelper.createNewCharacter("tester_"+i, true);
      assertNotNull(createdChar.getId());
      //Fetch available skills - and buy some
      int numSkillsBought = flowHelper.buySkills(createdChar.getId());
      //Check skills of fetched character
      CharacterDTO fetchedChar = flowHelper.getCharById(createdChar.getId());
      assertEquals(numSkillsBought, fetchedChar.getSkills().size());
    }
    int newCharCount = flowHelper.fetchAllCharacters().length;
    assertNotEquals(initalCharCount, newCharCount);
    assertEquals(initalCharCount+numChars, newCharCount);
  }

}
