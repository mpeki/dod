package dk.dodgame.util.character;

import dk.dodgame.data.BodyPartDTO;
import dk.dodgame.domain.character.model.body.BodyPartName;
import dk.dodgame.domain.character.model.body.HumanoidBody;
import java.io.Serial;
import java.util.Map;

public class BodyIncompleteException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = 5471118014817705667L;

  public BodyIncompleteException(Class<HumanoidBody> humanoidBodyClass, Map<BodyPartName, BodyPartDTO> bodyParts) {

    super("Body is incomplete " + humanoidBodyClass.getName() + " should contain: " + HumanoidBody.TOTAL_BODY_PARTS + ".\n"
        + "But only contains: " + bodyParts.size() + " body parts: " + bodyParts.keySet() + "\n");

  }
}
