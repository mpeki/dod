package dk.dodgame.domain.action.model;

import dk.dodgame.data.CharacterDTO;
import dk.dodgame.data.DODFact;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Action implements DODFact {
  Type type;
  Difficulty difficulty;
  CharacterDTO actor;
  ActionResult actionResult;
  String resultDescription;
}
