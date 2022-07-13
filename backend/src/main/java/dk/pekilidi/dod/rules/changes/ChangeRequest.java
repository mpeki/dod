package dk.pekilidi.dod.rules.changes;

import dk.pekilidi.dod.character.data.DODFact;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.With;

@Builder(toBuilder=true)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
@With
public class ChangeRequest implements DODFact {

  private ChangeType changeType;
  private String changeDescription;
  private ChangeKey changeKey;
  private Object objectBeforeChange;
  private Object objectAfterChange;
  private Object modifier;

}
