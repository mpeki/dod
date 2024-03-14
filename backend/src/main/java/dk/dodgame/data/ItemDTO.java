package dk.dodgame.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import dk.dodgame.domain.character.model.BaseTraitName;
import dk.dodgame.domain.character.model.body.BodyPartName;
import dk.dodgame.domain.item.ItemKey;
import dk.dodgame.domain.item.model.ItemType;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;

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
  private Integer abs;
  private List<BodyPartName> bodyPartsCovered;
  private Integer coverage;
  private Integer dodgePenalty;
  private Double price;
  private Double weight;
  private String weightReference;
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
