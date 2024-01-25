package dk.dodgame.util.rules;

import static java.lang.Math.ceil;

import dk.dodgame.domain.action.model.ActionResult;
import dk.dodgame.domain.action.model.Difficulty;
import dk.dodgame.data.CharacterSkillDTO;
import dk.dodgame.data.SkillDTO;
import dk.dodgame.domain.skill.model.Category;
import dk.dodgame.system.rule.DoDRule;
import dk.dodgame.util.Dice;

public class RulesUtil {

  private RulesUtil() {
  }

  public static int calculateGroupValue(int value) {
    if (value < 0) {
      return -1;
    }
    return switch (value) {
      case 0, 1, 2, 3 -> 0;
      case 4, 5, 6, 7, 8 -> 1;
      case 9, 10, 11, 12 -> 2;
      case 13, 14, 15, 16 -> 3;
      case 17, 18, 19, 20 -> 4;
      case 21, 22, 23, 24, 25 -> 5;
      case 26, 27, 28, 29, 30 -> 6;
      default -> value / 10 + (value % 10 == 0 ? 3 : 4);
    };
  }
}