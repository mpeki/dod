package dk.dodgame.domain.changerequest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SecondaryChangeKey {
  private ChangeType changeType;
  private ChangeKey changeKey;
}
