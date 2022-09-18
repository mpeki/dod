package dk.pekilidi.dod.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dk.pekilidi.dod.character.model.body.BodyPartName;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BodyPartDTO implements Serializable {

  private static final long serialVersionUID = 6802315925684473259L;

  @JsonIgnore
  @NonNull
  private BodyPartName name;
  private Integer maxHP;
  private Integer currentHP;
}
