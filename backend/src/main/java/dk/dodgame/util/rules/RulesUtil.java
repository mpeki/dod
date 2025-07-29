package dk.dodgame.util.rules;

import lombok.extern.slf4j.Slf4j;

import dk.dodgame.data.combat.Fight;
import dk.dodgame.domain.action.model.Actor;
import dk.dodgame.domain.character.model.body.BodyPartName;
import dk.dodgame.util.Dice;
import dk.dodgame.util.character.CharacterUtil;

@Slf4j
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

  public static boolean isBetween(int x, int lower, int upper) {
	  return lower <= x && x <= upper;
  }

  public static BodyPartName determineBodyPartName(Actor target) {
	  int roll = Dice.roll("1t20");
	  return switch (roll) {
		  case 1, 2 -> BodyPartName.RIGHT_LEG;
		  case 3, 4 -> BodyPartName.LEFT_LEG;
		  case 5, 6 -> BodyPartName.STOMACH;
		  case 7, 8 -> BodyPartName.CHEST;
		  case 9, 10, 11, 12 -> BodyPartName.RIGHT_ARM;
		  case 13, 14, 15, 16 -> BodyPartName.LEFT_ARM;
		  case 17, 18, 19, 20 -> BodyPartName.HEAD;
		  default -> {
			  log.warn("Unexpected roll value: {}. Defaulting to RIGHT_ARM.", roll);
			  yield BodyPartName.RIGHT_ARM;
		  }
	  };
  }

	public static void handleEndOfTurnEvents(Fight fight) {
	  log.info("Handling end of turn events for fight: {}", fight.getRef());
	  CharacterUtil.checkBleeding(fight.getListCharacters());
	}

  public static void debugFight(Fight fight) {
	  log.info("Fight: {}", fight.getRef());
	  log.info("Fighters health: {}", fight.getRef());
  }

}