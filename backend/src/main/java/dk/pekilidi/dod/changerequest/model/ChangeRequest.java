package dk.pekilidi.dod.changerequest.model;

import dk.pekilidi.dod.data.DODFact;
import jakarta.validation.constraints.NotNull;
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

  @NotNull
  private ChangeType changeType;
  private String changeDescription;
  @NotNull
  private ChangeKey changeKey;
  private SecondaryChangeKey secondaryChangeKey;
  private Object objectBeforeChange;
  private Object objectAfterChange;
  @NotNull
  private Object modifier;
  @Default
  private ChangeStatus status = ChangeStatus.PENDING;
  @Default
  private ChangeStatusLabel statusLabel = ChangeStatusLabel.CHANGE_REQUEST_PENDING;
}
