package dk.dodgame.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import dk.dodgame.domain.character.model.BaseTraitName;
import dk.dodgame.domain.item.ItemKey;
import dk.dodgame.domain.skill.SkillKey;
import dk.dodgame.domain.skill.model.Category;
import dk.dodgame.domain.skill.model.Group;
import java.io.Serial;
import java.io.Serializable;
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
  private BaseTraitName traitName;
  private Category category;
  private Group group;
  private Integer price;
  private BaseTraitName baseChance;
  private List<RaceDTO> deniedRaces;
}
