// <llm-snippet-file>backend/src/main/java/dk/dodgame/util/Dice.java</llm-snippet-file>
package dk.dodgame.util;

import java.security.SecureRandom;

public class Dice {

	public static final String PLAIN_REGEX = "[t]";
	public static final String ADD_REGEX   = "[t+]";
	public static final String MULTI_REGEX = "[tx]";

	private Dice() { }

	/* --------------------------------------------------------
	 * PUBLIC API
	 * -------------------------------------------------------- */

	/** Roll the dice expression once and return the result. */
	public static int roll(String dieSpec) {
		if (isBlankOrNone(dieSpec)) {
			return 0;
		}
		dieSpec = dieSpec.trim().toLowerCase();

		/*  NEW  ──────────────────────────────────────────── */
		if (dieSpec.matches("\\d+")) {            // constant value, e.g. "5"
			return Integer.parseInt(dieSpec);
		}
		/*  ──────────────────────────────────────────────── */

		if (dieSpec.contains("+")) {
			return doAdditionRoll(dieSpec);
		} else if (dieSpec.contains("x")) {
			return doMultiplication(dieSpec);
		} else {
			return doDefault(dieSpec);
		}
	}

	/** Return the maximum value the expression can ever yield. */
	public static int max(String dieSpec) {
		if (isBlankOrNone(dieSpec)) {
			return 0;
		}
		dieSpec = dieSpec.trim().toLowerCase();

		/*  NEW  ──────────────────────────────────────────── */
		if (dieSpec.matches("\\d+")) {            // constant value, e.g. "5"
			return Integer.parseInt(dieSpec);
		}
		/*  ──────────────────────────────────────────────── */

		if (dieSpec.contains("+")) {
			return doAdditionMax(dieSpec);
		} else if (dieSpec.contains("x")) {
			return doMultiplicationMax(dieSpec);
		} else {
			return doDefaultMax(dieSpec);
		}
	}

	/* --------------------------------------------------------
	 * INTERNAL HELPERS
	 * -------------------------------------------------------- */

	private static boolean isBlankOrNone(String s) {
		return s == null || s.isBlank()
				|| "none".equalsIgnoreCase(s)
				|| "na".equalsIgnoreCase(s);
	}

	/* -------- max() helpers -------- */

	/** Maximum for simple “2t6”-style specs or a tolerant subset thereof. */
	private static int doDefaultMax(String dieSpec) {
		String[] parts = dieSpec.split(PLAIN_REGEX, 2); // [ #dice , #sides ]
		int dice  = parts.length >= 1 && !parts[0].isBlank() ? Integer.parseInt(parts[0]) : 1;
		int sides = parts.length == 2 && !parts[1].isBlank() ? Integer.parseInt(parts[1]) : 1;
		return dice * sides;
	}

	private static int doMultiplicationMax(String dieSpec) {
		String[] parts = dieSpec.split(MULTI_REGEX, 3); // [ #dice , #sides , multiplier? ]
		int dice  = parts.length >= 1 && !parts[0].isBlank() ? Integer.parseInt(parts[0]) : 1;
		int sides = parts.length >= 2 && !parts[1].isBlank() ? Integer.parseInt(parts[1]) : 1;

		int result = dice * sides;
		if (parts.length == 3 && !parts[2].isBlank()) {
			result *= Integer.parseInt(parts[2]);
		}
		return result;
	}

	private static int doAdditionMax(String dieSpec) {
		if (dieSpec.startsWith("+")) {            // "+5" or "+2t6"
			String rest = dieSpec.substring(1).trim();
			if (rest.matches("\\d+")) {
				return Integer.parseInt(rest);
			}
			return doDefaultMax(rest);
		}

		String[] parts = dieSpec.split(ADD_REGEX, 3);  // [ #dice , #sides , bonus? ]
		int dice  = parts.length >= 1 && !parts[0].isBlank() ? Integer.parseInt(parts[0]) : 1;
		int sides = parts.length >= 2 && !parts[1].isBlank() ? Integer.parseInt(parts[1]) : 1;

		int result = dice * sides;
		if (parts.length == 3 && !parts[2].isBlank()) {
			result += Integer.parseInt(parts[2]);
		}
		return result;
	}

	/* -------- roll() helpers -------- */

	private static int doDefault(String dieSpec) {
		String[] parts = dieSpec.split(PLAIN_REGEX, 2); // [ #dice , #sides ]
		int dice  = parts.length >= 1 && !parts[0].isBlank() ? Integer.parseInt(parts[0]) : 1;
		int sides = parts.length == 2 && !parts[1].isBlank() ? Integer.parseInt(parts[1]) : 1;

		int result = 0;
		for (int i = 0; i < dice; i++) {
			result += rollOnce(sides);
		}
		return result;
	}

	private static int doMultiplication(String dieSpec) {
		String[] parts = dieSpec.split(MULTI_REGEX, 3); // [ #dice , #sides , multiplier? ]
		int dice  = parts.length >= 1 && !parts[0].isBlank() ? Integer.parseInt(parts[0]) : 1;
		int sides = parts.length >= 2 && !parts[1].isBlank() ? Integer.parseInt(parts[1]) : 1;

		int result = 0;
		for (int i = 0; i < dice; i++) {
			result += rollOnce(sides);
		}
		if (parts.length == 3 && !parts[2].isBlank()) {
			result *= Integer.parseInt(parts[2]);
		}
		return result;
	}

	private static int doAdditionRoll(String dieSpec) {
		if (dieSpec.startsWith("+")) {            // "+5" or "+2t6"
			String rest = dieSpec.substring(1).trim();
			return rest.matches("\\d+") ? Integer.parseInt(rest) : doDefault(rest);
		}

		String[] parts = dieSpec.split(ADD_REGEX, 3);  // [ #dice , #sides , bonus? ]
		int dice  = parts.length >= 1 && !parts[0].isBlank() ? Integer.parseInt(parts[0]) : 1;
		int sides = parts.length >= 2 && !parts[1].isBlank() ? Integer.parseInt(parts[1]) : 1;

		int result = 0;
		for (int i = 0; i < dice; i++) {
			result += rollOnce(sides);
		}
		if (parts.length == 3 && !parts[2].isBlank()) {
			result += Integer.parseInt(parts[2]);
		}
		return result;
	}

	/* -------- raw random roll -------- */

	private static int rollOnce(int sides) {
		return new SecureRandom().nextInt(Math.max(1, sides)) + 1;
	}
}