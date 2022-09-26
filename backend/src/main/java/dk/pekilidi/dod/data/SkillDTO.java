package dk.pekilidi.dod.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dk.pekilidi.dod.character.model.BaseTraitName;
import dk.pekilidi.dod.skill.model.Category;
import dk.pekilidi.dod.skill.model.Group;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SkillDTO implements Serializable {

  private static final long serialVersionUID = -5785515732736578828L;

  @JsonIgnore
  private Long id;

  private String key;
  private BaseTraitName traitName;
  private Category category;
  private Group group;
  private Integer price;
  private BaseTraitName baseChance;

}
