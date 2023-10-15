package dk.pekilidi.dod.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
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

  SkillDTO skill;
  String skillName;
  private Integer fv;
  private Integer experience;
  private Integer skillPointsSpent;
  private LocalDateTime lastUsed;


}
