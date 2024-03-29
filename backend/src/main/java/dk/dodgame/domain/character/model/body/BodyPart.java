package dk.dodgame.domain.character.model.body;

import java.io.Serial;
import java.io.Serializable;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

//@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@Builder
public class BodyPart implements Serializable {

  @Serial
  private static final long serialVersionUID = -8205287635113078391L;

  private Integer maxHP;
  private Integer currentHP;
}

