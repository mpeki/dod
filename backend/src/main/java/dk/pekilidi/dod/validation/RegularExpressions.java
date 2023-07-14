package dk.pekilidi.dod.validation;

public class RegularExpressions {
  public static final String VALID_CHARACTER_NAME = "^(?:Mr\\. |Mrs\\. )?(?:[A-Za-z]+[- ]?)*[A-Za-z]+(?: Jr\\.)?$";
  public static final String VALID_CHARACTER_HEIGHT = "\\d{2,3}";
  public static final String VALID_CHARACTER_WEIGHT = "\\d{2,3}";

  private RegularExpressions() {
    throw new IllegalStateException("Utility class");
  }
}
