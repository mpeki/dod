package dk.pekilidi.dod.changerequest.model;

public enum ChangeStatusLabel {

  OK_ITEM_BOUGHT("ok.item.bought"),
  INSUFFICIENT_FUNDS("not.enough.funds"),
  ITEM_DOES_NOT_EXIST("item.does.not.exist"),
  SKILL_DOES_NOT_EXIST("skill.does.not.exist"),
  SKILL_ALREADY_BOUGHT("skill.already.bought"),
  OK_SKILL_BOUGHT("ok.skill.bought"),
  OK_READY_FOR_PLAY("ok.ready.for.play"),
  TOO_MANY_SKILLS_POINTS("too.many.skills.points"),
  WRONG_CHARACTER_STATE("wrong.character.state"),
  NOT_IMPLEMENTED("change.type.not.implemented"),
  INSUFFICIENT_HERO_POINTS("not.enough.hero.points"),
  INSUFFICIENT_SKILL_POINTS("not.enough.skill.points"),
  INSUFFICIENT_EXP_POINTS("not.enough.exp.points"),
  NO_HERO("not.a.hero"),
  OK_HERO_POINTS_INCREASE("ok.hero.point.increase"),
  OK_BASE_TRAIT_MODIFICATION("ok.base.trait.modification"),
  CHANGE_REQUEST_PENDING("change.request.pending"),
  USER_DOES_NOT_HAVE_SKILL("user.does.not.have.skill"),
  OK_FV_INCREASED("ok.fv.increased"),
  NO_RULES_FIRED("no.rules.fired"),
  CHANGE_REJECTED_UNKNOWN_REASON("rejected.unknow.reason");
  public final String label;

  ChangeStatusLabel(String label) {
    this.label = label;
  }
}
