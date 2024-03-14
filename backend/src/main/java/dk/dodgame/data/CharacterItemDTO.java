package dk.dodgame.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import dk.dodgame.domain.character.model.ItemLocation;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class CharacterItemDTO implements DODFact, Serializable {

  @Serial
  private static final long serialVersionUID = 8735666145601434345L;

  private ItemDTO item;
  private String itemName;
  private ItemLocation location;
  private LocalDateTime timeAcquired;
  private LocalDateTime timeCreated;
  @Default
  private int currentHealth = 100;
  @Default
  private int quantity = 1;
  private Double currentWeight;
  private Double currentPrice;
}
