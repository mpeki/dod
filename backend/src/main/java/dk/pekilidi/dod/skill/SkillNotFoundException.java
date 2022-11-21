package dk.pekilidi.dod.skill;

public class SkillNotFoundException extends RuntimeException {

  public SkillNotFoundException() {
    super();
  }

  public SkillNotFoundException(String message) {
    super(message);
  }
}
