package dk.dodgame.domain.skill;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import dk.dodgame.data.BaseTraitDTO;
import dk.dodgame.data.CharacterDTO;
import dk.dodgame.data.CharacterSkillDTO;
import dk.dodgame.domain.character.model.BaseTraitName;
import dk.dodgame.domain.skill.model.Category;
import dk.dodgame.domain.skill.model.Group;
import dk.dodgame.domain.skill.model.Skill;
import dk.dodgame.data.SkillDTO;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
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
  @CsvFileSource(resources = "/data/cost2grp3-hero-data.csv", numLinesToSkip = 1)
  void getNewSkillPriceRange_cost2_grp3_hero(int fv, int cost) {
    testChar.setHero(true);
    List<Integer> range = SkillService.getNewSkillPriceRange(testChar, testSkill);
    assertEquals(cost, range.get(fv));
  }

  @ParameterizedTest
  @CsvFileSource(resources = "/data/cost2-nohero-data.csv", numLinesToSkip = 1)
  void getNewSkillPriceRange_cost2_grp1_nohero(int fv, int cost) {
    testChar.setHero(false);
    List<Integer> range = SkillService.getNewSkillPriceRange(testChar, testSkill);
    assertEquals(cost, range.get(fv));
  }

  @ParameterizedTest
  @CsvFileSource(resources = "/data/B_skill_cost8-nohero-data.csv", numLinesToSkip = 1)
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
  @Test
  void getSkillByBaseChance() {
    given(skillRepo.findByBaseChance(BaseTraitName.CHARISMA)).willReturn(Arrays.asList(modelMapper.map(testSkill, Skill.class)));
    List<SkillDTO> skillsFound = skillService.getSkillsByBaseChance(BaseTraitName.CHARISMA);
    assertEquals(testSkill, skillsFound.getFirst());
  }
  @Test
  void getSkillByGroup() {
    given(skillRepo.findByGroup(Group.COMBAT)).willReturn(Arrays.asList(modelMapper.map(testSkill, Skill.class)));
    List<SkillDTO> skillsFound = skillService.getSkillsByGroup(Group.COMBAT);
    assertEquals(testSkill, skillsFound.getFirst());
  }

  @Test
  void getSkillsByGroupAndBaseChance() {
    given(skillRepo.findByGroupAndBaseChance(Group.COMBAT, BaseTraitName.CHARISMA)).willReturn(Arrays.asList(modelMapper.map(testSkill, Skill.class)));
    List<SkillDTO> skillsFound = skillService.getSkillsByGroupAndBaseChance(Group.COMBAT, BaseTraitName.CHARISMA);
    assertEquals(testSkill, skillsFound.getFirst());
  }

  @Test
  void testCalculateNewSkillPriceIllegalCatBPurchase(){
    testSkill.setCategory(Category.B);
    testSkill.setPrice(8);
    testChar.setHero(false);
    assertThrows(IllegalArgumentException.class, () -> SkillService.calculateNewSkillPrice(testChar, testSkill, 6));
  }

  @ParameterizedTest
  @CsvFileSource(resources = "/data/fvIncreaseCost.csv", numLinesToSkip = 1)
  void testFvIncreaseCost(int startFv, int buyFv, int cost, int expected){
    SkillKey key = SkillKey.builder().value("primary.weapon").build();
    CharacterDTO testChar = CharacterDTO.builder()
        .skills(Map.of(key.getKeyValue(), CharacterSkillDTO
            .builder().skill(SkillDTO.builder().key(key).price(cost).build()).fv(startFv).build()))
        .build();
    assertEquals(expected, SkillService.calculatePriceForFVIncrease(testChar, key.getKeyValue(), buyFv));
  }
}
