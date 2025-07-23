package dk.dodgame.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

import dk.dodgame.data.CharacterDTO;
import dk.dodgame.data.RaceDTO;
import dk.dodgame.data.SkillDTO;
import dk.dodgame.domain.changerequest.model.ChangeRequest;
import dk.dodgame.domain.changerequest.model.ChangeStatus;
import dk.dodgame.domain.changerequest.model.ChangeStatusLabel;
import dk.dodgame.domain.changerequest.model.ChangeType;
import dk.dodgame.domain.character.model.AgeGroup;
import dk.dodgame.domain.character.model.BaseTraitName;
import dk.dodgame.domain.character.model.CharacterState;
import dk.dodgame.domain.skill.SkillService;
import dk.dodgame.domain.skill.model.Category;

@Slf4j
public class FlowTestHelper {

	private final String serviceUrl;
	private final HttpHeaders headers;
	private final RestTemplate restTemplate;

	public FlowTestHelper(String authUrl, String serviceUrl, HttpHeaders headers) {
		this.serviceUrl = serviceUrl;
		ObjectMapper mapper = new ObjectMapper()
				.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		restTemplate = new RestTemplateBuilder()
				.additionalMessageConverters(
					new MappingJackson2HttpMessageConverter(mapper),
					new FormHttpMessageConverter(),
					new StringHttpMessageConverter()
				)
				.build();
		try {
			headers.setBearerAuth(getAuthToken(authUrl));
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
		this.headers = headers;
	}

	String getAuthToken(String authUrl) throws JsonProcessingException {

		String authRequest = "username=msp&password=msp123&client_id=dodgame-api&grant_type=password";
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/x-www-form-urlencoded");
		HttpEntity<String> authRequestEntity = new HttpEntity<>(authRequest, headers);
		
		// Use Map instead of String to properly deserialize the JSON response
		ResponseEntity<Map> authResponse = restTemplate.postForEntity(
				authUrl, authRequestEntity, Map.class);

		assertEquals(HttpStatus.OK, authResponse.getStatusCode());
		assertNotNull(authResponse.getBody());

		// Extract access token directly from the map
		String authToken = (String) authResponse.getBody().get("access_token");
		assertNotNull(authToken, "Access token not found in response");

		log.info("Auth response: {}", authToken);
		return authToken;
	}

	void assertDeleted(String charId) {
		String getCharacterUrl = serviceUrl + "/char/" + charId;
		HttpClientErrorException thrown = Assertions.assertThrows(
				HttpClientErrorException.class, () -> restTemplate.exchange(getCharacterUrl, GET, new HttpEntity<>(headers), Void.class));
		assertEquals(HttpStatus.NOT_FOUND, thrown.getStatusCode());
	}

	CharacterDTO createNewCharacter(String name, boolean isHero) {
		return createNewCharacter(name, isHero, HttpStatus.OK, true);
	}

	String[] createManyCharacters(int numChar, String race) {
		String bulkCreateCharactersUrl = serviceUrl + "/char/bulk/create/" + numChar + "/" + race;
		HttpEntity<List<String>> request = new HttpEntity<>(headers);
		ResponseEntity<String[]> createdResponse = restTemplate.postForEntity(bulkCreateCharactersUrl, request, String[].class);
		return createdResponse.getBody();
	}

	CharacterDTO createNewCharacter(String name, boolean isHero, HttpStatus expectedStatus, boolean doAsserts) {
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

		assertEquals(expectedStatus, createdResponse.getStatusCode());
		if (doAsserts) {
			assertNotNull(createdResponse.getBody());
			assertEquals(CharacterState.INIT_COMPLETE, createdResponse.getBody().getState());
		}

		return createdResponse.getBody();
	}

	CharacterDTO getCharById(String charId) {
		String getCharacterUrl = serviceUrl + "/char/" + charId;
		ResponseEntity<CharacterDTO> getResponse = restTemplate.exchange(getCharacterUrl, GET, new HttpEntity<>(headers), CharacterDTO.class);
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
		HttpEntity<ChangeRequest> changeRequest = new HttpEntity<>(buySkillRequest, headers);
		ResponseEntity<ChangeRequest> buySkillResponse = restTemplate.postForEntity(
				changeCharUrl, changeRequest, ChangeRequest.class, headers);

		assertEquals(HttpStatus.OK, buySkillResponse.getStatusCode());
		return buySkillResponse;
	}

	SkillDTO[] fetchAvailableSkills() {
		String fetchSkillsUrl = serviceUrl + "/skill";
		ResponseEntity<SkillDTO[]> skillsResponse = restTemplate.exchange(fetchSkillsUrl, GET, new HttpEntity<>(headers), SkillDTO[].class);
		return skillsResponse.getBody();
	}

	ResponseEntity<ChangeRequest> makeCharacterReadyToPlay(String charId) {
		String changeCharUrl = serviceUrl + "/change/char/" + charId;
		ChangeRequest readyForPlayRequest = ChangeRequest
				.builder()
				.changeDescription("Ready to play")
				.changeType(ChangeType.CHARACTER_READY_TO_PLAY)
				.changeKey(CharacterState.READY_TO_PLAY)
				.build();

		HttpEntity<ChangeRequest> changeRequest = new HttpEntity<>(readyForPlayRequest, headers);
		ResponseEntity<ChangeRequest> response = restTemplate.postForEntity(
				changeCharUrl, changeRequest, ChangeRequest.class, headers);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		return response;
	}

	void deleteCharacter(String charId) {
		String deleteCharacterUrl = serviceUrl + "/char/" + charId;
		restTemplate.exchange(deleteCharacterUrl, DELETE, new HttpEntity<>(headers), Void.class);
		//Make sure it is gone!
		assertDeleted(charId);
	}

	public CharacterDTO[] fetchAllCharacters() {
		String fetchAllCharactersUrl = serviceUrl + "/char";

		ResponseEntity<CharacterDTO[]> skillsResponse = restTemplate.exchange(
				fetchAllCharactersUrl, GET, new HttpEntity<>(headers), CharacterDTO[].class);

		return skillsResponse.getBody();
	}
}
