package dk.dodgame.domain.items.model;

import static org.junit.jupiter.api.Assertions.*;

import dk.dodgame.domain.item.model.Coin;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("regression")
class CoinTest {

  @Test
  void fromKey() {
    assertEquals(Coin.GOLD_PIECE, Coin.fromKey("gold"));
    assertEquals(Coin.SILVER_PIECE, Coin.fromKey("silver"));
    assertEquals(Coin.COPPER_PIECE, Coin.fromKey("copper"));
    assertThrows(IllegalArgumentException.class, () -> Coin.fromKey("unknown"));
  }

  @Test
  void setType() {
    Coin coin = Coin.builder().type(Coin.Type.GOLD).build();
    assertEquals(Coin.GOLD_PIECE.getType(), coin.getType());
  }
}