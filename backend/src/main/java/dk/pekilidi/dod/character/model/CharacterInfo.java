package dk.pekilidi.dod.character.model;

import dk.pekilidi.dod.changerequest.model.ChangeKey;
import java.io.Serializable;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum CharacterInfo implements ChangeKey, Serializable {
  NAME, SOCIAL_STATUS, PROFESSION, LIFE_GOALS;

}
