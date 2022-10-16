package dk.pekilidi.dod.skill;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import dk.pekilidi.dod.character.model.BaseTraitName;
import dk.pekilidi.dod.data.BaseTraitDTO;
import dk.pekilidi.dod.data.CharacterDTO;
import dk.pekilidi.dod.data.SkillDTO;
import dk.pekilidi.dod.skill.model.Category;
import dk.pekilidi.dod.skill.model.Skill;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.modelmapper.ModelMapper;

@Tag("regression")
class SkillServiceTest {

  private static final ModelMapper modelMapper = new ModelMapper();
  CharacterDTO testChar;
  SkillDTO testSkill;
  SkillKey skillKey;

  private SkillRepository skillRepo;
  private SkillService skillService;

  @BeforeEach
  void setup() {
    skillRepo = mock(SkillRepository.class);
    skillService = new SkillService(skillRepo);
    skillKey = SkillKey.builder().value("primary.weapon").build();

    testChar = new CharacterDTO();
    testChar.addBaseTrait(
        BaseTraitDTO.builder().traitName(BaseTraitName.INTELLIGENCE).currentValue(17).groupValue(3).build());
    testSkill = SkillDTO
        .builder()
        .key(skillKey)
        .category(Category.A)
        .price(2)
        .baseChance(BaseTraitName.NONE)
        .traitName(BaseTraitName.INTELLIGENCE)
        .build();
  }

  @Test
  void calculateNewSkillPrice() {

    testChar.setHero(true);

    assertEquals(14, SkillService.calculateNewSkillPrice(testChar, testSkill, 10));
    assertEquals(0, SkillService.calculateNewSkillPrice(testChar, testSkill, 1));
    assertEquals(28, SkillService.calculateNewSkillPrice(testChar, testSkill, 15));
    assertEquals(32, SkillService.calculateNewSkillPrice(testChar, testSkill, 16));
    assertEquals(54, SkillService.calculateNewSkillPrice(testChar, testSkill, 20));
    assertThrows(IllegalArgumentException.class, () -> SkillService.calculateNewSkillPrice(testChar, testSkill, 21));
  }

  @ParameterizedTest
  @CsvFileSource(resources = "/cost2grp3-hero-data.csv", numLinesToSkip = 1)
  void getNewSkillPriceRange_cost2_grp3_hero(int fv, int cost) {
    testChar.setHero(true);
    List<Integer> range = SkillService.getNewSkillPriceRange(testChar, testSkill);
    assertEquals(cost, range.get(fv));
  }

  @ParameterizedTest
  @CsvFileSource(resources = "/cost2-nohero-data.csv", numLinesToSkip = 1)
  void getNewSkillPriceRange_cost2_grp1_nohero(int fv, int cost) {
    testChar.setHero(false);
    List<Integer> range = SkillService.getNewSkillPriceRange(testChar, testSkill);
    assertEquals(cost, range.get(fv));
  }

  @ParameterizedTest
  @CsvFileSource(resources = "/B_skill_cost8-nohero-data.csv", numLinesToSkip = 1)
  void getNewSkillPriceRange_CATB_cost8_grp1_nohero(int fv, int cost) {
    testChar.setHero(false);
    testSkill.setPrice(8);
    testSkill.setCategory(Category.B);
    List<Integer> range = SkillService.getNewSkillPriceRange(testChar, testSkill);
    assertEquals(cost, range.get(fv));
  }

  @Test
  void findSkillBySkillKey() {
    given(skillRepo.findByKey(skillKey)).willReturn(modelMapper.map(testSkill, Skill.class));
    SkillDTO skillFound = skillService.findSkillByKey(skillKey);
    assertEquals(testSkill, skillFound);
  }
}
