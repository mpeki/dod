package dk.dodgame.domain.item.model;

import static dk.dodgame.domain.item.model.Coin.Type.*;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Transient;
import java.io.Serial;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@Entity
@DiscriminatorValue("5")
@SuperBuilder(toBuilder = true)
public class Coin extends ManyPiece {

  @Serial
  private static final long serialVersionUID = -2679556597707767616L;

  public static final Coin GOLD_PIECE = Coin.builder().type(GOLD).build();
  public static final Coin SILVER_PIECE = Coin.builder().type(SILVER).build();
  public static final Coin COPPER_PIECE = Coin.builder().type(COPPER).build();

  @Override
  public String keyOf() {
    return type.keyOf();
  }

  @Transient
  private Coin.Type type;
  @AllArgsConstructor
  public enum Type {
    GOLD("gold"), SILVER("silver"), COPPER("copper");
    private final String key;

    public String keyOf() {
      return key;
    }
  }

  public static Coin fromKey(String key) {
    return switch (key) {
      case "gold" -> GOLD_PIECE;
      case "silver" -> SILVER_PIECE;
      case "copper" -> COPPER_PIECE;
      default -> throw new IllegalArgumentException("Unknown coin type: " + key);
    };
  }


}


