package dk.dodgame.domain.character.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serial;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class Movement  implements Serializable {

  @Serial
  private static final long serialVersionUID = 6938773713031825934L;

  @Default
  @Column(columnDefinition = "int default -1")
  int speedOnLand = -1;
  @Default
  @Column(columnDefinition = "int default -1")
  int speedInWater = -1;
  @Default
  @Column(columnDefinition = "int default -1")
  int speedInAir = -1;
}
