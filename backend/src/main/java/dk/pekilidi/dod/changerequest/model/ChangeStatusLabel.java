package dk.pekilidi.dod.changerequest.model;

public enum ChangeStatusLabel {

  SKILL_ALREADY_BOUGHT("skill.already.bought"),
  OK_SKILL_BOUGHT("ok.skill.bought"),
  NOT_IMPLEMENTED("change.type.not.implemented"),
  INSUFFICIENT_HERO_POINTS("not.enough.hero.points"),
  INSUFFICIENT_SKILL_POINTS("not.enough.skill.points"),
  NO_HERO("not.a.hero"),
  OK_HERO_POINTS_INCREASE("ok.hero.point.increase"),
  OK_BASE_TRAIT_MODIFICATION("ok.base.trait.modification"),
  CHANGE_REQUEST_PENDING("change.request.pending"),
  CHANGE_REJECTED_UNKNOWN_REASON("rejected.unknow.reason");
  public final String label;

  ChangeStatusLabel(String label) {
    this.label = label;
  }
}
