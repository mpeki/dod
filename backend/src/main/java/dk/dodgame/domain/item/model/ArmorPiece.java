package dk.dodgame.domain.item.model;

import dk.dodgame.domain.character.model.body.BodyPartName;
import dk.dodgame.domain.character.model.body.BodyPartNameListConverter;
import dk.dodgame.domain.item.ItemKey;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.UniqueConstraint;
import java.io.Serial;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue("6")
@SuperBuilder(toBuilder = true)
public class ArmorPiece extends BaseItem {

  @Serial
  private static final long serialVersionUID = 2194420277906182455L;

  private Integer abs;

  @Convert(converter = BodyPartNameListConverter.class)
  @Column(columnDefinition = "VARCHAR(25)")
  private List<BodyPartName> bodyPartsCovered;

  private Integer coverage;
  private Integer dodgePenalty;

  @Column(name = "armor_type")
  private ArmorType type;
  private String weightReference;

}
