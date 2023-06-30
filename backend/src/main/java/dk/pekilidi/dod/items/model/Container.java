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
@DiscriminatorValue("0")
@SuperBuilder(toBuilder = true)
public class Container extends BaseItem {

  @Serial
  private static final long serialVersionUID = 451192037122986190L;
  private int bepStorage;
}
