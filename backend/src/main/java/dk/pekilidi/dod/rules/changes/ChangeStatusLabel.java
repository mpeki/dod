package dk.pekilidi.dod.rules.changes;

public enum ChangeStatusLabel {

  INSUFFICIENT_HERO_POINTS("not.enough.hero.points"),
  OK_BASE_TRAIT_MODIFICATION("ok.base.trait.modification");

  public final String label;

  ChangeStatusLabel(String label) {
    this.label = label;
  }
}
