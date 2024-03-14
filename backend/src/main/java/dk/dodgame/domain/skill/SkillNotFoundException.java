package dk.dodgame.domain.skill;

import java.io.Serial;

public class SkillNotFoundException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = -6497825281187430935L;

  public SkillNotFoundException() {
    super();
  }

  public SkillNotFoundException(String message) {
    super(message);
  }
}
