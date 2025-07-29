package dk.dodgame.util;

import java.security.SecureRandom;

public class Dice {

	public static final String PLAIN_REGEX = "[t]";
	public static final String ADD_REGEX = "[t+]";
	public static final String MULTI_REGEX = "[tx]";

	private Dice() {
	}

	public static int roll(String dieSpec) {
		if (dieSpec == null || dieSpec.isBlank() || "none".equalsIgnoreCase(dieSpec) || "na".equalsIgnoreCase(dieSpec)) {
			return 0;
		}
		int result;
		if (dieSpec.contains("+")) {
			result = doAdditionRoll(dieSpec);
		} else if (dieSpec.contains("x")) {
			result = doMultiplication(dieSpec);
		} else {
			result = doDefault(dieSpec);
		}
		return result;
	}

	/**
	 * Computes the maximum value that the die specification can yield.
	 * <ul>
	 *   <li>2t6       → 12</li>
	 *   <li>2t4x100   → 800</li>
	 *   <li>3t6+3     → 21</li>
	 *   <li>+5        → 5</li>
	 *   <li>none/na/""→ 0</li>
	 * </ul>
	 */
	public static int max(String dieSpec) {
		if (dieSpec == null || dieSpec.isBlank()
				|| "none".equalsIgnoreCase(dieSpec)
				|| "na".equalsIgnoreCase(dieSpec)) {
			return 0;
		}
		dieSpec = dieSpec.trim().toLowerCase();
		if (dieSpec.contains("+")) {
			return doAdditionMax(dieSpec);
		} else if (dieSpec.contains("x")) {
			return doMultiplicationMax(dieSpec);
		} else {
			return doDefaultMax(dieSpec);
		}
	}

	/* existing roll helpers omitted for brevity */

	/* ----------------- Helpers for max() ----------------- */

	/** Maximum for simple “2t6”–style specs. */
	private static int doDefaultMax(String dieSpec) {
		String[] parts = dieSpec.split(PLAIN_REGEX, 2); // #dice , #sides
		int dice  = Integer.parseInt(parts[0]);
		int sides = Integer.parseInt(parts[1]);
		return dice * sides;
	}

	/** Maximum when the spec has a multiplier, e.g. “2t4x100”. */
	private static int doMultiplicationMax(String dieSpec) {
		String[] parts = dieSpec.split(MULTI_REGEX, 3); // #dice , #sides , multiplier?
		int dice  = Integer.parseInt(parts[0]);
		int sides = Integer.parseInt(parts[1]);

		int result = dice * sides;    // highest total on the dice
		if (parts.length == 3) {
			int multiplier = Integer.parseInt(parts[2]);
			result *= multiplier;
		}
		return result;
	}

	/**
	 * Maximum when the spec has an addition, e.g. “3t6+3”.
	 * Handles a leading “+” as a constant bonus: “+5” → 5; “+2t6” → 12.
	 */
	private static int doAdditionMax(String dieSpec) {
		if (dieSpec.startsWith("+")) {
			String rest = dieSpec.substring(1).trim();
			if (rest.matches("\\d+")) {
				return Integer.parseInt(rest);        // just a flat bonus
			}
			return doDefaultMax(rest);                // “+2t6” → 12
		}

		String[] parts = dieSpec.split(ADD_REGEX, 3); // #dice , #sides , bonus?
		int dice  = Integer.parseInt(parts[0]);
		int sides = Integer.parseInt(parts[1]);

		int result = dice * sides;
		if (parts.length == 3) {
			result += Integer.parseInt(parts[2]);
		}
		return result;
	}
	private static int doDefault(String dieSpec) {
		int result = 0;
		String[] dieSpecArr = dieSpec.toLowerCase().split(PLAIN_REGEX, 2);
		for (int i = 0; i < Integer.parseInt(dieSpecArr[0]); i++) {
			result += roll(Integer.parseInt(dieSpecArr[1]));
		}
		return result;
	}

	private static int doMultiplication(String dieSpec) {
		int result = 0;
		String[] dieSpecArr = dieSpec.toLowerCase().split(MULTI_REGEX, 3);
		for (int i = 0; i < Integer.parseInt(dieSpecArr[0]); i++) {
			result += roll(Integer.parseInt(dieSpecArr[1]));
		}
		if (dieSpecArr.length == 3) {
			result = result * Integer.parseInt(dieSpecArr[2]);
		}
		return result;
	}

	private static int doAdditionRoll(String dieSpec) {
		int result = 0;
		if(dieSpec.startsWith("+")){
			String d = dieSpec.substring(1).trim();
			if (!d.matches("\\d+")) {
			    return doDefault(d);
			} else {
				return Integer.parseInt(d);
			}
		}
		String[] dieSpecArr = dieSpec.toLowerCase().split(ADD_REGEX, 3);
		System.out.println("here i am: " + dieSpecArr.length);
		for (int i = 0; i < Integer.parseInt(dieSpecArr[0]); i++) {
			result += roll(Integer.parseInt(dieSpecArr[1]));
		}
		if (dieSpecArr.length == 3) {
			result += Integer.parseInt(dieSpecArr[2]);
		}
		return result;
	}

	private static int roll(int eyes) {
		return new SecureRandom().nextInt(eyes) + 1;
	}
}

