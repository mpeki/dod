package dk.dodgame.domain.changerequest.model;

import dk.dodgame.domain.changerequest.model.ChangeKey;
import jakarta.transaction.NotSupportedException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
@Tag("regression")
class ChangeKeyTest {

  @Test
  void testValueOf() {
    Assertions.assertThrows(NotSupportedException.class, () -> {
      ChangeKey.valueOf("test");
    });
  }

  @Test
  void testValueOfNoArg() {
    Assertions.assertThrows(NotSupportedException.class, ChangeKey::valueOf);
  }

}