package dk.pekilidi.dod.changerequest.model;

public enum ChangeType {

  BASE_TRAIT("character-change"),
  NEW_SKILL("new-skill"),
  SKILL_CHANGE("skill-change"),
  HIT_POINTS("character-change"),
  HERO_POINTS("character-change"),
  BASE_SKILL_POINTS("character-change");

  public final String changeRuleSet;

  ChangeType(String changeRuleSet) {
    this.changeRuleSet = changeRuleSet;
  }
}
