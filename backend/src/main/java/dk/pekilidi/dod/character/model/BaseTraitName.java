package dk.pekilidi.dod.character.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import dk.pekilidi.dod.changerequest.model.ChangeKey;
import dk.pekilidi.dod.changerequest.model.ChangeKeyDeserializer;
import java.io.Serializable;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@JsonDeserialize(using = ChangeKeyDeserializer.class)
public enum BaseTraitName implements ChangeKey, Serializable {

  NONE, STRENGTH, SIZE, CONSTITUTION, DEXTERITY, INTELLIGENCE, PSYCHE, CHARISMA;

  private static final long serialVersionUID = -4787939824473814512L;
}
