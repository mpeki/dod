package dk.pekilidi.dod.changerequest.model;

public enum ChangeType {

  BASE_TRAIT(ChangeType.CHARACTER_CHANGE),
  NEW_SKILL("new-skill"),
  SKILL_CHANGE(ChangeType.CHARACTER_SKILL_CHANGE),
  NEW_ITEM("new-item"),
  BUY_FV(ChangeType.CHARACTER_SKILL_CHANGE),
  HIT_POINTS(ChangeType.CHARACTER_CHANGE),
  HERO_POINTS(ChangeType.CHARACTER_CHANGE),
  BASE_SKILL_POINTS(ChangeType.CHARACTER_CHANGE),
  CHARACTER_READY_TO_PLAY("character-state-change"),
  SKILL_FOR_ITEM_USE("skill-for-item-use"),
  CHARACTER_CHANGE_HEIGHT(ChangeType.CHARACTER_CHANGE),
  CHARACTER_CHANGE_WEIGHT(ChangeType.CHARACTER_CHANGE),
  CHARACTER_CHANGE_NAME(ChangeType.CHARACTER_CHANGE),
  REMOVE_SKILL("remove-skill");

  public final String changeRuleSet;
  private static final String CHARACTER_CHANGE = "character-change";
  private static final String CHARACTER_SKILL_CHANGE = "skill-change";

  ChangeType(String changeRuleSet) {
    this.changeRuleSet = changeRuleSet;
  }
}
