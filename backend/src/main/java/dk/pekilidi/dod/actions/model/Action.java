package dk.pekilidi.dod.actions.model;

import dk.pekilidi.dod.data.DODFact;
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
  ActionResult actionResult;
  String resultDescription;
}
