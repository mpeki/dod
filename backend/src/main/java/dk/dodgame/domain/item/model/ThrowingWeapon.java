package dk.dodgame.domain.item.model;

import dk.dodgame.domain.character.model.BaseTraitName;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.io.Serial;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue("2")
@SuperBuilder(toBuilder = true)
public class ThrowingWeapon extends Weapon {

  @Serial
  private static final long serialVersionUID = 7717458412779865459L;
  @NonNull BaseTraitName rangeTrait;
  @NonNull Integer rangeMultiplier;
  @NonNull Integer maxThrowDistance;
}
