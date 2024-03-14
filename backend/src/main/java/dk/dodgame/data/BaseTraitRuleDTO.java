package dk.dodgame.data;

import dk.dodgame.domain.character.model.BaseTraitName;

import java.io.Serial;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BaseTraitRuleDTO implements Serializable {

  @Serial
  private static final long serialVersionUID = 217444570883418038L;

  private BaseTraitName baseTraitName;
  private String baseTraitDieRoll;
  private String baseTraitHeroDieRoll;
}
