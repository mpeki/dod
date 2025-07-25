package dk.dodgame.domain.character.model;

import java.io.Serial;
import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import lombok.*;
import lombok.Builder.Default;

import dk.dodgame.util.ExcludeFromCoverageReportGenerated;

@EqualsAndHashCode
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
@ExcludeFromCoverageReportGenerated
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

//Todo - currently I have two movement classes, clean this up.