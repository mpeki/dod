package dk.dodgame.data;

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
public class CharacterTemplateDTO implements Serializable {

  @Serial
  private static final long serialVersionUID = 1529438121369404567L;
  private String id;
  private String name;
  private List<BaseTraitRuleDTO> baseTraitRules;
  private Class<?> bodyTypeClass;
}
