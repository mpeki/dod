package dk.pekilidi.dod.character.model.body;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import dk.pekilidi.dod.changerequest.model.ChangeKey;
import dk.pekilidi.dod.changerequest.model.ChangeKeyDeserializer;
import java.io.Serializable;

@JsonDeserialize(using = ChangeKeyDeserializer.class)
public enum BodyPartName implements ChangeKey, Serializable {
  TOTAL, HEAD, CHEST, STOMACH, LEFT_ARM, RIGHT_ARM, LEFT_LEG, RIGHT_LEG;
  private static final long serialVersionUID = -4787939824473814511L;
}
