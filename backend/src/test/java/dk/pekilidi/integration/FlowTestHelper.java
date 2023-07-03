package dk.pekilidi.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import org.junit.jupiter.api.Assertions;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

public class FlowTestHelper {

  private final String serviceUrl;
  private final HttpHeaders headers;
  private final RestTemplate restTemplate = new RestTemplate();
  public FlowTestHelper(String serviceUrl, HttpHeaders headers) {
    this.serviceUrl = serviceUrl;
    this.headers = headers;
  }

  void assertDeleted(String charId) {
    String getCharacterUrl = serviceUrl + "/char/" + charId;
    HttpClientErrorException thrown = Assertions.assertThrows(
        HttpClientErrorException.class, () -> restTemplate.getForObject(getCharacterUrl, Void.class));
    assertEquals(HttpStatus.NOT_FOUND, thrown.getStatusCode());
  }

  CharacterDTO createNewCharacter(String name, boolean isHero) {
    String createCharacterUrl = serviceUrl + "/char";
    CharacterDTO character = CharacterDTO
        .builder()
        .name(name)
        .race(RaceDTO.builder().name("human").build())
        .ageGroup(AgeGroup.MATURE)
        .hero(isHero)
        .build();

    HttpEntity<CharacterDTO> request = new HttpEntity<>(character, headers);
    ResponseEntity<CharacterDTO> createdResponse = restTemplate.postForEntity(
        createCharacterUrl, request, CharacterDTO.class);
    assertEquals(HttpStatus.OK, createdResponse.getStatusCode());
    assertNotNull(createdResponse.getBody());
    assertEquals(CharacterState.INIT_COMPLETE, createdResponse.getBody().getState());

    return createdResponse.getBody();
  }

  CharacterDTO getCharById(String charId) {
    String getCharacterUrl = serviceUrl + "/char/" + charId;

    ResponseEntity<CharacterDTO> getResponse = restTemplate.getForEntity(getCharacterUrl, CharacterDTO.class);
    assertEquals(HttpStatus.OK, getResponse.getStatusCode());
    return getResponse.getBody();
  }

  CharacterDTO buyHeroPoints(String charId, int amountToBuy) {
    String changeCharUrl = serviceUrl + "/change/char/" + charId;

    ChangeRequest increaseHeroPoints = ChangeRequest
        .builder()
        .changeDescription("Increase hero points")
        .changeType(ChangeType.HERO_POINTS)
        .modifier(amountToBuy)
        .build();

    HttpEntity<ChangeRequest> changeRequest = new HttpEntity<>(increaseHeroPoints, headers);
    ResponseEntity<ChangeRequest> changeResponse = restTemplate.postForEntity(
        changeCharUrl, changeRequest, ChangeRequest.class);
    assertEquals(HttpStatus.OK, changeResponse.getStatusCode());
    assertNotNull(changeResponse.getBody());
    assertEquals(ChangeStatus.APPROVED, changeResponse.getBody().getStatus());
    assertEquals(ChangeStatusLabel.OK_HERO_POINTS_INCREASE, changeResponse.getBody().getStatusLabel());
    return getCharById(charId);
  }

  CharacterDTO buyBaseTraitIncrease(String charId, BaseTraitName baseTrait, int pointToBuy) {
    String changeCharUrl = serviceUrl + "/change/char/" + charId;
    ChangeRequest increaseBaseTrait = ChangeRequest
        .builder()
        .changeDescription("Increase base trait STRENGTH")
        .changeType(ChangeType.BASE_TRAIT)
        .modifier(pointToBuy)
        .changeKey(baseTrait)
        .build();

    HttpEntity<ChangeRequest> changeRequest = new HttpEntity<>(increaseBaseTrait, headers);
    ResponseEntity<ChangeRequest> changeResponse = restTemplate.postForEntity(
        changeCharUrl, changeRequest, ChangeRequest.class);
    assertEquals(HttpStatus.OK, changeResponse.getStatusCode());
    assertNotNull(changeResponse.getBody());
    assertEquals(ChangeStatus.APPROVED, changeResponse.getBody().getStatus());
    assertEquals(ChangeStatusLabel.OK_BASE_TRAIT_MODIFICATION, changeResponse.getBody().getStatusLabel());
    return getCharById(charId);
  }

  int buySkills(String charId) throws InterruptedException {
    int numSkillsBought = 0;
    for (SkillDTO skill : fetchAvailableSkills()) {

      int fvToBuy = skill.getCategory() == Category.A ? 15 : 3;
      CharacterDTO fetchedChar = getCharById(charId);
      ResponseEntity<ChangeRequest> buySkillResponse = buySkill(charId, skill, fvToBuy);
      assertNotNull(buySkillResponse.getBody());
      if (SkillService.calculateNewSkillPrice(fetchedChar, skill, fvToBuy) > fetchedChar.getBaseSkillPoints()) {
        assertEquals(ChangeStatus.REJECTED, buySkillResponse.getBody().getStatus());
        assertEquals(ChangeStatusLabel.INSUFFICIENT_SKILL_POINTS, buySkillResponse.getBody().getStatusLabel());
        break;
      } else {
        assertEquals(ChangeStatus.APPROVED, buySkillResponse.getBody().getStatus());
        assertEquals(ChangeStatusLabel.OK_SKILL_BOUGHT, buySkillResponse.getBody().getStatusLabel());
        numSkillsBought++;
      }
    }
    return numSkillsBought;
  }

  ResponseEntity<ChangeRequest> buySkill(String charId, SkillDTO skill, int fvToBuy) {
    String changeCharUrl = serviceUrl + "/change/char/" + charId;

    ChangeRequest buySkillRequest = ChangeRequest
        .builder()
        .changeDescription("Buy another skill...")
        .changeType(ChangeType.NEW_SKILL)
        .modifier(fvToBuy)
        .changeKey(skill.getKey())
        .build();
    //Buy skills
    ResponseEntity<ChangeRequest> buySkillResponse = restTemplate.postForEntity(
        changeCharUrl, buySkillRequest, ChangeRequest.class);

    assertEquals(HttpStatus.OK, buySkillResponse.getStatusCode());
    return buySkillResponse;
  }

  SkillDTO[] fetchAvailableSkills() {
    String fetchSkillsUrl = serviceUrl + "/skill";
    ResponseEntity<SkillDTO[]> skillsResponse = restTemplate.getForEntity(fetchSkillsUrl, SkillDTO[].class);
    return skillsResponse.getBody();
  }

  ResponseEntity<ChangeRequest>  makeCharacterReadyToPlay(String charId){
    String changeCharUrl = serviceUrl + "/change/char/" + charId;
    ChangeRequest readyForPlayRequest = ChangeRequest
        .builder()
        .changeDescription("Ready to play")
        .changeType(ChangeType.CHARACTER_READY_TO_PLAY)
        .changeKey(CharacterState.READY_TO_PLAY)
        .build();

    ResponseEntity<ChangeRequest> response = restTemplate.postForEntity(
        changeCharUrl, readyForPlayRequest, ChangeRequest.class);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    return response;
  }

  void deleteCharacter(String charId) {
    String deleteCharacterUrl = serviceUrl + "/char/" + charId;
    restTemplate.delete(deleteCharacterUrl);
    //Make sure it is gone!
    assertDeleted(charId);
  }

  public CharacterDTO[] fetchAllCharacters() {
    String fetchAllCharactersUrl = serviceUrl + "/char";
    ResponseEntity<CharacterDTO[]> skillsResponse = restTemplate.getForEntity(
        fetchAllCharactersUrl, CharacterDTO[].class);
    return skillsResponse.getBody();
  }
}
