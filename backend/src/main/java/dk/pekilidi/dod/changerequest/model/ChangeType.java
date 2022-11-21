package dk.pekilidi.dod.changerequest.model;

public enum ChangeType {

  //constant character-change

  BASE_TRAIT(ChangeType.CHARACTER_CHANGE),
  NEW_SKILL("new-skill"),
  SKILL_CHANGE("skill-change"),
  HIT_POINTS(ChangeType.CHARACTER_CHANGE),
  HERO_POINTS(ChangeType.CHARACTER_CHANGE),
  BASE_SKILL_POINTS(ChangeType.CHARACTER_CHANGE);

  public final String changeRuleSet;
  private static final String CHARACTER_CHANGE = "character-change";

  ChangeType(String changeRuleSet) {
    this.changeRuleSet = changeRuleSet;
  }
}
