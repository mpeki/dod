package dk.pekilidi.dod.items.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.io.Serial;
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
@DiscriminatorValue("1")
@SuperBuilder(toBuilder = true)
public class ProjectileWeapon extends Weapon {

  @Serial
  private static final long serialVersionUID = 9113243766657758269L;
  Integer bowCast;
}
