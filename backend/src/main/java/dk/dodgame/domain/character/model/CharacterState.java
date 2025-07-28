package dk.dodgame.domain.character.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import dk.dodgame.domain.changerequest.model.ChangeKey;
import dk.dodgame.domain.changerequest.model.ChangeKeyDeserializer;
import java.io.Serial;
import java.io.Serializable;
import lombok.AllArgsConstructor;
@AllArgsConstructor
@JsonDeserialize(using = ChangeKeyDeserializer.class)
public enum CharacterState implements ChangeKey, Serializable {
  NEW, BASE_TRAITS_SET, AGE_GROUP_SET, TOTAL_HP_SET, BODY_PART_HP_SET, DAMAGE_BONUS_SET, INIT_COMPLETE, READY_TO_PLAY, IN_PLAY, INCAPACITATED, DEAD;
  @Serial
  private static final long serialVersionUID = -4787939824473814510L;
}
