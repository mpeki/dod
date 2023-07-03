package dk.pekilidi.dod.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import dk.pekilidi.dod.character.model.BaseTraitName;
import dk.pekilidi.dod.items.ItemKey;
import dk.pekilidi.dod.items.model.ItemType;
import java.io.Serial;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_EMPTY)
public class ItemDTO implements Serializable {

  @Serial
  private static final long serialVersionUID = 7824679358143392362L;

  private ItemType itemType;
  @JsonIgnore
  private String id;
  @Default
  private boolean breakable = true;
  private ItemKey itemKey;
  private Integer price;
  private Double weight;
  private Integer piecesForPrice;
  private Integer bp;
  private String damage;
  private String handGrip;
  private Integer length;
  private Integer strengthGroup;
  private Integer bowCast;
  private Integer bepStorage;
  private Integer maxThrowDistance;
  private Integer rangeMultiplier;
  private BaseTraitName rangeTrait;
}
