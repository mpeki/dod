package dk.pekilidi.dod.character.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import dk.pekilidi.dod.changerequest.model.ChangeKey;
import dk.pekilidi.dod.changerequest.model.ChangeKeyDeserializer;
import java.io.Serializable;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@JsonDeserialize(using = ChangeKeyDeserializer.class)
public enum CharacterInfo implements ChangeKey, Serializable {
  NAME, HEIGHT, WEIGHT, SOCIAL_STATUS, PROFESSION, LIFE_GOALS;

}
