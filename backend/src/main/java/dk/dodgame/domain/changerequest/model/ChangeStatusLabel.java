package dk.dodgame.domain.changerequest.model;

public enum ChangeStatusLabel {

  SKILL_NOT_BOUGHT("skill.not.bought"),
  OK_ITEM_BOUGHT("ok.item.bought"),
  OK_ITEM_REMOVED("ok.item.removed"),
  FUNDING_MISMATCH("payment.funding.mismatch"),
  INSUFFICIENT_FUNDS("payment.insufficient.funds"),
  NO_PAYMENT_RECEIVED("payment.not.received"),
  NO_PAYING_PARTY("payment.no.paying.party"),
  PAYMENT_ERROR("payment.error"),
  PAYMENT_PENDING("payment.pending"),
  PAYMENT_TOO_LARGE("payment.too.large"),
  NO_PRICE_CALCULATED("payment.no.price"),
  NO_ITEMS_IN_PAYMENT("payment.no.items"),
  ITEM_DOES_NOT_EXIST("item.does.not.exist"),
  ITEM_NOT_IN_INVENTORY("item.is.not.in.inventory"),
  SKILL_DOES_NOT_EXIST("skill.does.not.exist"),
  SKILL_ALREADY_BOUGHT("skill.already.bought"),
  OK_SKILL_BOUGHT("ok.skill.bought"),
  OK_SKILL_REMOVED("ok.skill.removed"),
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
  CHANGE_REJECTED_UNKNOWN_REASON("rejected.unknow.reason"),
  OK_NAME("valid.name"),
  INVALID_NAME("invalid.name"),
  INVALID_HEIGHT("invalid.height"),
  INVALID_WEIGHT("invalid.weight"),
  OK_HEIGHT("valid.height"),
  OK_WEIGHT("valid.weight");

  public final String label;

  ChangeStatusLabel(String label) {
    this.label = label;
  }
}
