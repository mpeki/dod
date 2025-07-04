package dk.dodgame.domain.skill;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
//import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import dk.dodgame.BaseControllerTest;
import dk.dodgame.domain.character.model.BaseTraitName;
import dk.dodgame.domain.skill.SkillKey;
import dk.dodgame.domain.skill.SkillService;
import dk.dodgame.domain.skill.model.Category;
import dk.dodgame.domain.skill.model.Group;
import dk.dodgame.data.SkillDTO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.request.ParameterDescriptor;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@Tag("regression")
class SkillResourceApiDocTest extends BaseControllerTest {

  List<SkillDTO> testListResult = new ArrayList<>(1);
  SkillDTO testSkill;
  private MockMvc mockMvc;
  @MockBean
  private SkillService skillService;

  @BeforeEach
  void setup(RestDocumentationContextProvider restDocumentation) throws Exception {
    mockMvc = MockMvcBuilders
        .webAppContextSetup(context)
        .apply(documentationConfiguration(restDocumentation).uris().withPort(8090))
        .alwaysDo(document("{class-name}/{method-name}/", preprocessRequest(prettyPrint()),
            preprocessResponse(prettyPrint())))
        .build();

    testSkill = SkillDTO
        .builder()
        .skillId("123")
        .key(SkillKey.builder().value("primary.weapon").build())
        .traitName(BaseTraitName.DEXTERITY)
        .baseChance(BaseTraitName.DEXTERITY)
        .group(Group.COMBAT)
        .category(Category.A)
        .build();
    testListResult.add(testSkill);
  }

/*  @Test
  public void index() throws Exception {
    this.mockMvc
        .perform(get("/").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(document("{class-name}/{method-name}/", preprocessRequest(prettyPrint()), preprocessResponse
        (prettyPrint()),
            links(linkWithRel("skill").description("The SKILL resource")),
            responseFields(subsectionWithPath("_links").description("Links to other resources")), responseHeaders(
                headerWithName("Content-Type").description(
                    "The Content-Type of the payload, e.g. `application/json`"))));
  }*/

  @Test
  void getSkills() throws Exception {
    ConstraintDescriptions desc = new ConstraintDescriptions(SkillDTO.class);

    when(skillService.getSkillsByGroupAndBaseChance(Group.COMBAT, BaseTraitName.DEXTERITY)).thenReturn(testListResult);

    mockMvc
        .perform(get("/api/skill?group=COMBAT&baseChance=DEXTERITY").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(document("{class-name}/{method-name}", preprocessResponse(prettyPrint()),
            pathParameters(enumParam("group", Group.values()).optional(),
                enumParam("baseChance", BaseTraitName.values()).optional()),
            responseFields(fieldWithPath("[].key").type(SkillKey.class.getSimpleName()).description("The skill key"),
                enumField("[].group", Group.class.getSimpleName(), Group.values()),
                enumField("[].traitName", BaseTraitName.class.getSimpleName(), BaseTraitName.values()),
                enumField("[].baseChance", BaseTraitName.class.getSimpleName(), BaseTraitName.values()),
                enumField("[].category", Category.class.getSimpleName(), Category.values()))));
  }

  @Test
  void getSkillByKey() throws Exception {
    when(skillService.findSkillByKey("primary.weapon")).thenReturn(testSkill);

    mockMvc
        .perform(get("/api/skill/key/{key}", "primary.weapon").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(document("{class-name}/{method-name}", preprocessResponse(prettyPrint()),
            pathParameters(parameterWithName("key").description("The skill key")),
            responseFields(fieldWithPath("key").type(SkillKey.class.getSimpleName()).description("The skill key"),
                enumField("group", Group.class.getSimpleName(), Group.values()),
                enumField("traitName", BaseTraitName.class.getSimpleName(), BaseTraitName.values()),
                enumField("baseChance", BaseTraitName.class.getSimpleName(), BaseTraitName.values()),
                enumField("category", Category.class.getSimpleName(), Category.values()))));
  }

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
