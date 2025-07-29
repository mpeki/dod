package dk.dodgame.domain.action.model;

public enum Difficulty {
  EASY(2),
  NORMAL(1),
  HARD(.5),
  VERY_HARD(.25),
  EXTREMELY_HARD(.1),
  NEARLY_IMPOSSIBLE(0.05);

  private final double value;

  Difficulty(double value) {
    this.value = value;
  }

  public double getValue() {
    return value;
  }
}
