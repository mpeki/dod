package dk.pekilidi.dod.character.model;

import dk.pekilidi.dod.changerequest.model.ChangeKey;
import java.io.Serializable;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum BaseTraitName implements ChangeKey, Serializable {

  NONE, STRENGTH, SIZE, CONSTITUTION, DEXTERITY, INTELLIGENCE, PSYCHE, CHARISMA;

  private static final long serialVersionUID = -4787939824473814512L;
}
