package dk.dodgame.domain.item.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Transient;
import java.io.Serial;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@DiscriminatorValue("4")
@Entity
public class Projectile extends ManyPiece {

  public static final Projectile ARROW = Projectile.builder().type(Type.ARROW).build();
  public static final Projectile BOLT = Projectile.builder().type(Type.BOLT).build();
  public static final Projectile DART = Projectile.builder().type(Type.DART).build();
  public static final Projectile SLING_BULLET = Projectile.builder().type(Type.SLING_BULLET).build();

  @Serial
  private static final long serialVersionUID = 3377231375045088097L;

  @Transient
  private Type type;
  @Override
  public String keyOf() {
    return type.keyOf();
  }


  @AllArgsConstructor
  public enum Type {
    ARROW("arrow"), BOLT("bolt"), DART("dart"), SLING_BULLET("sling.bullet");
    private final String key;

    public String keyOf() {
      return key;
    }
  }
}
