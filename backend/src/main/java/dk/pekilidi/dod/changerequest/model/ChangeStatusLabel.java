package dk.pekilidi.dod.changerequest.model;

public enum ChangeStatusLabel {

  INSUFFICIENT_HERO_POINTS("not.enough.hero.points"),
  NO_HERO("not.a.hero"),
  OK_HERO_POINTS_INCREASE("ok.hero.point.increase"),
  OK_BASE_TRAIT_MODIFICATION("ok.base.trait.modification");

  public final String label;

  ChangeStatusLabel(String label) {
    this.label = label;
  }
}
