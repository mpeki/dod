package dk.dodgame.domain.character;

import static dk.dodgame.util.BaseTestUtil.TEST_OWNER;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.testcontainers.utility.Base58.randomString;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.request.ParameterDescriptor;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import dk.dodgame.BaseControllerTest;
import dk.dodgame.data.CharacterDTO;
import dk.dodgame.data.RaceDTO;
import dk.dodgame.domain.character.model.AgeGroup;
import dk.dodgame.domain.character.model.CharacterState;
import dk.dodgame.domain.character.model.FavoriteHand;
import dk.dodgame.domain.skill.SkillKey;

/**
 * CharacterResourceApiDocTest provides API documentation testing for the character-related endpoints.
 * It extends {@link BaseControllerTest} to inherit common test configuration and setup.
 * The test methods within this class document the API interactions for character operations.
 * <p>
 * The tests use `MockMvc` for performing HTTP requests and verifying responses, including generating
 * REST documentation snippets using Spring REST Docs.
 * <p>
 * This class is tagged as a "regression" test to categorize it as part of the regression test suite,
 * ensuring stability of the character-related API features.
 * <p>
 * An example of a character entity (testCharacter) is configured and reused within the tests to standardize
 * the expected responses.
 * <p>
 * Annotations and configurations:
 * - {@link ExtendWith}: Used with {@link RestDocumentationExtension} and {@link SpringExtension}.
 * - @Tag("regression"): Specifies the test suite designation.
 * - @MockBean: Mocks dependencies like {@link CharacterService} to control the behavior of service calls.
 * <p>
 * Test methods:
 * - {@code getCharacterById}: Documents and verifies the endpoint for retrieving a character by ID.
 * - {@code deleteCharacterById}: Documents and verifies the endpoint for deleting a character by ID.
 * <p>
 * Utility methods:
 * - {@code enumParam}: Creates a parameter descriptor for enum types, including context about valid enum values.
 * - {@code enumField}: Creates a field descriptor for enum types, listing all possible values.
 */
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@Tag("regression")
class CharacterResourceApiDocTest extends BaseControllerTest {

  CharacterDTO testCharacter;
  @Autowired
  private WebApplicationContext context;
  @MockitoBean
  private CharacterService service;

	@BeforeEach
	void setup(RestDocumentationContextProvider restDocumentation) {
		mockMvc = MockMvcBuilders
				.webAppContextSetup(context)
				.apply(springSecurity())
				.apply(documentationConfiguration(restDocumentation).uris().withPort(8090))
				.alwaysDo(document("{class-name}/{method-name}/", preprocessRequest(prettyPrint()),
						preprocessResponse(prettyPrint())))
				.build();

		testCharacter = CharacterDTO
				.builder()
				.name("Borgan den Blå")
				.ageGroup(AgeGroup.MATURE)
				.race(RaceDTO.builder().id(randomString(10)).name("Human").motherTongue(SkillKey.toSkillKey("common")).build())
				.baseSkillPoints(123)
				.favoriteHand(FavoriteHand.RIGHT)
				.hero(true)
				.state(CharacterState.INIT_COMPLETE)
				.build();
	}

	@Test
	@WithMockUser(username = TEST_OWNER, password = "player", roles = {"player"})
	void getCharacterById() throws Exception {

		when(service.findCharacterByIdAndOwner(anyString(), anyString())).thenReturn(testCharacter);

		mockMvc
				.perform(get("/api/char/{id}", "123").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andDo(document("{class-name}/{method-name}", preprocessResponse(prettyPrint()),
						pathParameters(parameterWithName("id").description("The character id")),
						relaxedResponseFields(
								fieldWithPath("name").description("The character name"),
								fieldWithPath("race").type(RaceDTO.class.getSimpleName()).description("Character race"),
								fieldWithPath("race.name").type(RaceDTO.class.getSimpleName()).description("Race name"),
								fieldWithPath("race.motherTongue").type(RaceDTO.class.getSimpleName()).description("Race mother tongue"),
								fieldWithPath("hero").type(Boolean.class.getSimpleName()).description("Is this a hero?"),
								fieldWithPath("heroPoints")
										.type(Integer.class.getSimpleName())
										.description("Hero points to spend, -1 if not a hero"),
								fieldWithPath("damageBonus").type(String.class.getSimpleName()).description("Additional damage roll"),
								fieldWithPath("baseSkillPoints").type(String.class.getSimpleName()).description("Used to by skills"),
								enumField("ageGroup", AgeGroup.class.getSimpleName(), AgeGroup.values()),
								enumField("favoriteHand", FavoriteHand.class.getSimpleName(), FavoriteHand.values()),
								enumField("state", CharacterState.class.getSimpleName(), CharacterState.values()),
								fieldWithPath("actorType").description("Always character for CharacterDTO"),
								subsectionWithPath("wield").description("Equipment currently worn or wielded, keyed by body location")
//								subsectionWithPath("wield.*[].item")
//										.description("The actual item object inside each slot")
//								fieldWithPath("wield.*[].itemName").description("Item’s translation key"),
//								fieldWithPath("wield.*[].currentHealth").description("Current durability (percent)"),
//								fieldWithPath("wield.*[].quantity").description("How many items are stacked in that slot")
						)));
	}

	@Test
	@WithMockUser(username = TEST_OWNER, password = "player", roles = {"player"})
	void deleteCharacterById() throws Exception {

		when(service.findCharacterByIdAndOwner("123", TEST_OWNER)).thenReturn(testCharacter);

		mockMvc
				.perform(delete("/api/char/{id}", 1L).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andDo(document("{class-name}/{method-name}", preprocessResponse(prettyPrint()),
						pathParameters(parameterWithName("id").description("The character id"))));
	}

/*
  @Test
  public void getSkillByKey() throws Exception {
    when(skillService.findSkillByKey("primary.weapon")).thenReturn(testSkill);

    mockMvc
        .perform(get("/skill/key/{key}", "primary.weapon").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(document("{class-name}/{method-name}", preprocessResponse(prettyPrint()),
            pathParameters(parameterWithName("key").description("The skill key")),
            responseFields(fieldWithPath("key").type(SkillKey.class.getSimpleName()).description("The skill key"),
                enumField("group", Group.class.getSimpleName(), Group.values()),
                enumField("traitName", BaseTraitName.class.getSimpleName(), BaseTraitName.values()),
                enumField("baseChance", BaseTraitName.class.getSimpleName(), BaseTraitName.values()),
                enumField("category", Category.class.getSimpleName(), Category.values()))));
  }
*/

  private ParameterDescriptor enumParam(String name, Object[] values) {
    String formattedEnumValues = Arrays
        .stream(values)
        .map("`%s`"::formatted)
        .collect(Collectors.joining(", "));
    return parameterWithName(name).description("Filter Skills returned by skill " + name + ": " + formattedEnumValues);
  }

  private FieldDescriptor enumField(String name, String fieldType, Object[] values) {
    String formattedEnumValues = Arrays
        .stream(values)
        .map("`%s`"::formatted)
        .collect(Collectors.joining(", "));
    return fieldWithPath(name).type(fieldType).description(formattedEnumValues);
  }
}
