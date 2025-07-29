package dk.dodgame.data;

import java.io.Serial;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;

import dk.dodgame.domain.character.model.body.BodyPartName;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BodyPartDTO implements Serializable {

  @Serial
  private static final long serialVersionUID = 6802315925684473259L;

  @JsonIgnore
  @NonNull
  private BodyPartName name;
  private Integer maxHP;
  private Integer currentHP;
  @Builder.Default
  private Integer percentLeft = 100;
}
