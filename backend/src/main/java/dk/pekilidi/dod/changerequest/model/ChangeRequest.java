package dk.pekilidi.dod.changerequest.model;

import dk.pekilidi.dod.data.DODFact;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.With;

@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
@Setter
@With
public class ChangeRequest implements DODFact {

  private ChangeType changeType;
  private String changeDescription;
  private ChangeKey changeKey;
  private Object objectBeforeChange;
  private Object objectAfterChange;
  private Object modifier;
  @Default
  private ChangeStatus status = ChangeStatus.PENDING;
  @Default
  private ChangeStatusLabel statusLabel = ChangeStatusLabel.CHANGE_REQUEST_PENDING;
}
