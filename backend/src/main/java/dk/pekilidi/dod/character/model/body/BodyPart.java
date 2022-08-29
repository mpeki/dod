package dk.pekilidi.dod.character.model.body;

import javax.persistence.Embeddable;
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
public class BodyPart {

  private Integer maxHP;
  private Integer currentHP;
}

