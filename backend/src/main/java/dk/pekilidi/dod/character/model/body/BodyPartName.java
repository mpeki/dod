package dk.pekilidi.dod.character.model.body;

import dk.pekilidi.dod.changerequest.model.ChangeKey;
import java.io.Serializable;

public enum BodyPartName implements ChangeKey, Serializable {
  TOTAL, HEAD, CHEST, STOMACH, LEFT_ARM, RIGHT_ARM, LEFT_LEG, RIGHT_LEG;
  private static final long serialVersionUID = -4787939824473814511L;
}
