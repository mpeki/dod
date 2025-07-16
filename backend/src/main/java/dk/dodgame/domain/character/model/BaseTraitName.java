package dk.dodgame.domain.character.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import dk.dodgame.domain.changerequest.model.ChangeKey;
import dk.dodgame.domain.changerequest.model.ChangeKeyDeserializer;
import java.io.Serial;
import java.io.Serializable;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@JsonDeserialize(using = ChangeKeyDeserializer.class)
public enum BaseTraitName implements ChangeKey, Serializable {

  NONE, STRENGTH, SIZE, CONSTITUTION, DEXTERITY, INTELLIGENCE, PSYCHE, CHARISMA, SPECIAL;

  @Serial
  private static final long serialVersionUID = -4787939824473814512L;
}
