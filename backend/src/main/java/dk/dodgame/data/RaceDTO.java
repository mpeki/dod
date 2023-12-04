package dk.dodgame.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dk.dodgame.domain.skill.SkillKey;
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
  private SkillKey motherTongue;
  @JsonIgnore
  private CharacterTemplateDTO characterTemplate;
}
