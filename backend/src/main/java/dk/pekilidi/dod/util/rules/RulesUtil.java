package dk.pekilidi.dod.util.rules;

import static java.lang.Math.ceil;

import dk.pekilidi.dod.actions.model.Difficulty;
import dk.pekilidi.dod.actions.model.ActionResult;
import dk.pekilidi.dod.util.Dice;

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

  public static ActionResult testSkill(int fv, int roll, Difficulty difficulty) {
    int modifiedFv = (int) ceil(fv * difficulty.getValue());
    if (modifiedFv < roll) {
      if (roll == 20 && Dice.roll("1t20") > modifiedFv) {
        return ActionResult.FUMBLE;
      }
      return ActionResult.FAILURE;
    } else {
      if (roll <= ((int) ceil((double) modifiedFv / 3))) {
        return ActionResult.MASTERFUL;
      } else if (roll == 1 && Dice.roll("1t20") < modifiedFv) {
        return ActionResult.PERFECT;
      }
      return ActionResult.SUCCESS;
    }
  }
}