package dk.dodgame.util.rule;

import static org.assertj.core.api.Assertions.assertThat;

import dk.dodgame.domain.action.model.ActionResult;
import dk.dodgame.domain.action.model.Difficulty;
import dk.dodgame.data.CharacterSkillDTO;
import dk.dodgame.data.SkillDTO;
import dk.dodgame.domain.skill.model.Category;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import dk.dodgame.util.rules.RulesUtil;
import dk.dodgame.util.rules.SkillRules;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

@Tag("regression")
class RulesUtilTest {

  @Test
  void testConstructorPrivate() {
    Assertions.assertThrows(IllegalAccessException.class, () -> RulesUtil.class.getDeclaredConstructor().newInstance());
  }

  @Test
  void callPrivateConstructorsForCodeCoverage()
      throws SecurityException, NoSuchMethodException, IllegalArgumentException, InstantiationException,
      IllegalAccessException, InvocationTargetException {
    Class<?>[] classesToConstruct = {RulesUtil.class};
    for (Class<?> clazz : classesToConstruct) {
      Constructor<?> constructor = clazz.getDeclaredConstructor();
      constructor.setAccessible(true);
      Assertions.assertNotNull(constructor.newInstance());
    }
  }

  @ParameterizedTest
  @CsvSource(value = {
      "-10,-1",
      "-1,-1",
      "0,0",
      "1,0",
      "2,0",
      "3,0",
      "4,1",
      "5,1",
      "7,1",
      "8,1",
      "9,2",
      "10,2",
      "11,2",
      "12,2",
      "13,3",
      "15,3",
      "16,3",
      "17,4",
      "18,4",
      "19,4",
      "20,4",
      "21,5",
      "22,5",
      "24,5",
      "25,5",
      "26,6",
      "28,6",
      "29,6",
      "30,6",
      "31,7",
      "32,7",
      "40,7",
      "41,8",
      "42,8",
      "51,9",
      "52,9",
      "53,9",
      "59,9",
      "60,9",
      "61,10",
      "62,10",
      "70,10",
      "124,16"})
  void calculateGroupValue(int fv, int expectedGroup) {
    assertThat(RulesUtil.calculateGroupValue(fv)).isEqualTo(expectedGroup);
  }

  @ParameterizedTest()
  @CsvFileSource(resources = "/data/testSkillData.csv", numLinesToSkip = 1)
  void testCatASkill(int fv, int roll, Difficulty difficulty, ActionResult expectedResult) {
    CharacterSkillDTO skill = CharacterSkillDTO.builder()
        .skill(SkillDTO.builder().category(Category.A).build())
        .fv(fv)
        .build();
    assertThat(SkillRules.testSkill(skill, roll, difficulty)).isEqualTo(expectedResult);
  }

  @Test
  void testCatBSkill(){
    CharacterSkillDTO skill = CharacterSkillDTO.builder()
        .skill(SkillDTO.builder().category(Category.B).build())
        .fv(5)
        .build();
    assertThat(SkillRules.testSkill(skill, 19, Difficulty.NORMAL)).isEqualTo(ActionResult.SUCCESS);
    skill.setFv(4);
    assertThat(SkillRules.testSkill(skill, 19, Difficulty.NORMAL)).isEqualTo(ActionResult.SUCCESS);
    skill.setFv(3);
    assertThat(SkillRules.testSkill(skill, 17, Difficulty.NORMAL)).isEqualTo(ActionResult.SUCCESS);
    skill.setFv(2);
    assertThat(SkillRules.testSkill(skill, 17, Difficulty.NORMAL)).isEqualTo(ActionResult.FAILURE);
    skill.setFv(1);
    assertThat(SkillRules.testSkill(skill, 13, Difficulty.NORMAL)).isEqualTo(ActionResult.SUCCESS);
    skill.setFv(6);
    assertThat(SkillRules.testSkill(skill, 13, Difficulty.NORMAL)).isEqualTo(ActionResult.SUCCESS);
  }
}