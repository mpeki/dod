package dk.dodgame.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import dk.dodgame.domain.item.ItemKey;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class CharacterSkillDTO implements DODFact, Serializable {

  @Serial
  private static final long serialVersionUID = 2503382720100228168L;

  private SkillDTO skill;
  private ItemKey itemKey;
  private String skillName;
  private Integer fv;
  private Integer experience;
  @Default
  private Integer skillPointsSpent = 0;
  private LocalDateTime lastUsed;


}
