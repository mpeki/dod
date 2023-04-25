package dk.pekilidi.dod.character.model;

import dk.pekilidi.dod.changerequest.model.ChangeKey;
import java.io.Serial;
import java.io.Serializable;
import lombok.AllArgsConstructor;
@AllArgsConstructor
public enum CharacterState implements ChangeKey, Serializable {
  NEW, BASE_TRAITS_SET, AGE_GROUP_SET, TOTAL_HP_SET, BODY_PART_HP_SET, DAMAGE_BONUS_SET, INIT_COMPLETE, READY_TO_PLAY, DEAD;
  @Serial
  private static final long serialVersionUID = -4787939824473814510L;
}
