package dk.pekilidi.dod.items.model;

import dk.pekilidi.dod.items.model.Projectile.Type;
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
@SuperBuilder(toBuilder = true)
public abstract class ManyPiece extends BaseItem {

  @Serial
  private static final long serialVersionUID = 6047831652263378840L;
  private Integer piecesForPrice;

  public String keyOf(){
    throw new UnsupportedOperationException("Not implemented");
  };
}
