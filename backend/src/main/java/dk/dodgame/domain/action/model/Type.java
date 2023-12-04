package dk.dodgame.domain.action.model;

public enum Type {
  SKILL_TRAINING("training-action");

  private final String value;

  Type(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
