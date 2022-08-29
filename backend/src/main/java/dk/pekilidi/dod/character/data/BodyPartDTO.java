package dk.pekilidi.dod.character.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dk.pekilidi.dod.character.model.body.BodyPartName;
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
public class BodyPartDTO {

  @JsonIgnore
  @NonNull
  private BodyPartName name;
  private Integer maxHP;
  private Integer currentHP;
}
