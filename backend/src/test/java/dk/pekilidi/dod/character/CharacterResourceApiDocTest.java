package dk.pekilidi.dod.character;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.testcontainers.utility.Base58.randomString;

import dk.pekilidi.dod.BaseControllerTest;
import dk.pekilidi.dod.character.model.AgeGroup;
import dk.pekilidi.dod.character.model.CharacterState;
import dk.pekilidi.dod.character.model.FavoriteHand;
import dk.pekilidi.dod.data.CharacterDTO;
import dk.pekilidi.dod.data.RaceDTO;
import java.util.Arrays;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.request.ParameterDescriptor;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@Tag("regression")
class CharacterResourceApiDocTest extends BaseControllerTest {

  CharacterDTO testCharacter;
  @Autowired
  private WebApplicationContext context;
  @MockBean
  private CharacterService service;

  @BeforeEach
  void setup(RestDocumentationContextProvider restDocumentation) throws Exception {
    mockMvc = MockMvcBuilders
        .webAppContextSetup(context)
        .apply(documentationConfiguration(restDocumentation).uris().withPort(8090))
        .alwaysDo(document("{class-name}/{method-name}/", preprocessRequest(prettyPrint()),
            preprocessResponse(prettyPrint())))
        .build();

    testCharacter = CharacterDTO
        .builder()
        .name("Borgan den Blå")
        .ageGroup(AgeGroup.MATURE)
        .race(RaceDTO.builder().id(randomString(10)).name("Human").build())
        .baseSkillPoints(123)
        .favoriteHand(FavoriteHand.RIGHT)
        .hero(true)
        .state(CharacterState.INIT_COMPLETE)
        .build();
  }

  @Test
  void getCharacterById() throws Exception {

    when(service.findCharacterById("123")).thenReturn(testCharacter);

    mockMvc
        .perform(get("/api/char/{id}", "123").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(document("{class-name}/{method-name}", preprocessResponse(prettyPrint()),
            pathParameters(parameterWithName("id").description("The character id")),
            responseFields(fieldWithPath("name").description("The character name"),
                fieldWithPath("race").type(RaceDTO.class.getSimpleName()).description("Character race"),
                fieldWithPath("race.name").type(RaceDTO.class.getSimpleName()).description("Race name"),
                fieldWithPath("hero").type(Boolean.class.getSimpleName()).description("Is this a hero?"),
                fieldWithPath("heroPoints")
                    .type(Integer.class.getSimpleName())
                    .description("Hero points to spend, -1 if not a hero"),
                fieldWithPath("damageBonus").type(String.class.getSimpleName()).description("Additional damage roll"),
                fieldWithPath("baseSkillPoints").type(String.class.getSimpleName()).description("Used to by skills"),
                enumField("ageGroup", AgeGroup.class.getSimpleName(), AgeGroup.values()),
                enumField("favoriteHand", FavoriteHand.class.getSimpleName(), FavoriteHand.values()),
                enumField("state", CharacterState.class.getSimpleName(), CharacterState.values()))));
  }

  @Test
  void deleteCharacterById() throws Exception {

    when(service.findCharacterById("123")).thenReturn(testCharacter);

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
        .map(type -> String.format("`%s`", type))
        .collect(Collectors.joining(", "));
    return parameterWithName(name).description("Filter Skills returned by skill " + name + ": " + formattedEnumValues);
  }

  private FieldDescriptor enumField(String name, String fieldType, Object[] values) {
    String formattedEnumValues = Arrays
        .stream(values)
        .map(type -> String.format("`%s`", type))
        .collect(Collectors.joining(", "));
    return fieldWithPath(name).type(fieldType).description(formattedEnumValues);
  }
}
