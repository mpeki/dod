package dk.dodgame.domain.item.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.io.Serial;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@Entity
@DiscriminatorValue("3")
@SuperBuilder(toBuilder = true)
public class MeleeWeapon extends Weapon {
  @Serial
  private static final long serialVersionUID = -4552301806628474155L;
}
