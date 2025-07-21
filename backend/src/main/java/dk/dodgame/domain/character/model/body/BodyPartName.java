package dk.dodgame.domain.character.model.body;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import dk.dodgame.domain.changerequest.model.ChangeKey;
import dk.dodgame.domain.changerequest.model.ChangeKeyDeserializer;
import java.io.Serial;
import java.io.Serializable;

@JsonDeserialize(using = ChangeKeyDeserializer.class)
public enum BodyPartName implements ChangeKey, Serializable {
  TOTAL, HEAD, CHEST, STOMACH, LEFT_ARM, RIGHT_ARM, LEFT_LEG, RIGHT_LEG;
  @Serial
  private static final long serialVersionUID = -4787939824473814511L;
}
