package dk.pekilidi.dod.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import dk.pekilidi.dod.character.model.BaseTraitName;
import dk.pekilidi.dod.items.ItemKey;
import dk.pekilidi.dod.skill.SkillKey;
import dk.pekilidi.dod.skill.model.Category;
import dk.pekilidi.dod.skill.model.Group;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(Include.NON_NULL)
public class SkillDTO implements Serializable {

  @Serial
  private static final long serialVersionUID = -5785515732736578828L;

  @JsonIgnore
  private String skillId;
  //
  //  @JsonIgnore
  private SkillKey key;
  private ItemKey itemKey;
  private BaseTraitName traitName;
  private Category category;
  private Group group;
  private Integer price;
  private BaseTraitName baseChance;
  private Integer fv;
  private Integer experience;
  private Integer skillPointsSpent;
  private LocalDateTime lastUsed;
  private List<RaceDTO> deniedRaces;
}
