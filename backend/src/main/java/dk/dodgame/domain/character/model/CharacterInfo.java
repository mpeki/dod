package dk.dodgame.domain.character.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import dk.dodgame.domain.changerequest.model.ChangeKey;
import dk.dodgame.domain.changerequest.model.ChangeKeyDeserializer;
import java.io.Serializable;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@JsonDeserialize(using = ChangeKeyDeserializer.class)
public enum CharacterInfo implements ChangeKey, Serializable {
  NAME, HEIGHT, WEIGHT, SOCIAL_STATUS, PROFESSION, LIFE_GOALS;

}
