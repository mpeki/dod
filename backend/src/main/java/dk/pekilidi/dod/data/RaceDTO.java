package dk.pekilidi.dod.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serial;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RaceDTO implements Serializable {

  @Serial
  private static final long serialVersionUID = -2393069543783772239L;

  public RaceDTO(String name) {
    this.name = name;
    this.characterTemplate = new CharacterTemplateDTO();
  }

  @JsonIgnore
  private String id;
  private String name;
  @JsonIgnore
  private CharacterTemplateDTO characterTemplate;
}