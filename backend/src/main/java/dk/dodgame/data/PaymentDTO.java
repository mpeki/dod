package dk.dodgame.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import dk.dodgame.domain.changerequest.model.ChangeItem;
import dk.dodgame.domain.changerequest.model.ChangeStatus;
import dk.dodgame.domain.changerequest.model.ChangeStatusLabel;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentDTO implements ChangeItem {
  private int gold;
  private int silver;
  private int copper;
  @Builder.Default
  private List<CharacterItemDTO> itemsExchanged = new ArrayList<>();
  private CharacterDTO payingParty;
  private CharacterDTO receivingParty;
  @Builder.Default
  private ChangeStatus status = ChangeStatus.PENDING;
  @Builder.Default
  private ChangeStatusLabel statusLabel = ChangeStatusLabel.PAYMENT_PENDING;
}
