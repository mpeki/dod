package dk.dodgame.util.rules;

import static dk.dodgame.util.rules.RulesUtil.isBetween;

import java.math.RoundingMode;

import com.google.common.math.IntMath;

import dk.dodgame.data.CharacterDTO;
import dk.dodgame.domain.character.model.BaseTraitName;

public class CharacterCreationRules {

	/**
	 * Calculates the damage-bonus string for the supplied character and
	 * stores it back into the DTO. The bonus is also returned.
	 *
	 * @param character the character whose damage bonus should be (re)calculated
	 * @return the damage-bonus string, e.g. "-1", "+1t4", "none"
	 */
	public static String calculateDamageBonus(CharacterDTO character) {

		// --- 1. Gather input (null-guarded) -----------------------------------
		int strength = defaultZero(character.getBaseTraitValue(BaseTraitName.STRENGTH));
		int size     = defaultZero(character.getBaseTraitValue(BaseTraitName.SIZE));

		// --- 2. Average of SIZ+STR, rounded *up* ------------------------------
		int avgVal = (strength + size + 1) / 2;          // ceiling division by 2

		// Dwarves get a flat +2
		if ("dwarf".equalsIgnoreCase(character.getRace().getName())) {
			avgVal += 2;
		}

		// --- 3. Look-up table --------------------------------------------------
		final String bonus;
		if      (isBetween(avgVal,  1,  4)) bonus = "-1t2";
		else if (isBetween(avgVal,  5,  8)) bonus = "-1";
		else if (isBetween(avgVal,  9, 13)) bonus = "none";
		else if (isBetween(avgVal, 14, 15)) bonus = "+1";
		else if (isBetween(avgVal, 16, 17)) bonus = "+1t2";
		else if (isBetween(avgVal, 18, 20)) bonus = "+1t4";
		else if (isBetween(avgVal, 21, 23)) bonus = "+1t6";
		else if (isBetween(avgVal, 24, 26)) bonus = "+1t8";
		else if (isBetween(avgVal, 27, 30)) bonus = "+1t10";
		else if (isBetween(avgVal, 31, 35)) bonus = "+2t6";
		else if (isBetween(avgVal, 36, 40)) bonus = "+2t8";
		else if (isBetween(avgVal, 41, 45)) bonus = "+3t6";
		else if (isBetween(avgVal, 46, 50)) bonus = "+3t8";
		else if (isBetween(avgVal, 51, 60)) bonus = "+4t6";
		else {                                   // 61+
			int diceCount = (avgVal / 10) - 2;   // 61-70 ⇒ 4t6, 71-80 ⇒ 5t6 …
			bonus = "+" + diceCount + "t6";
		}

		// --- 4. Persist and return --------------------------------------------
		character.setDamageBonus(bonus);
		return bonus;
	}

	/* ---------------------------------------------------------------------- */

	/** Safe-unwrap for nullable Integer → primitive int. */
	private static int defaultZero(Integer value) {
		return value == null ? 0 : value;
	}

	/** Inclusive range-check helper. */
	private static boolean isBetween(int value, int low, int high) {
		return value >= low && value <= high;
	}

}
