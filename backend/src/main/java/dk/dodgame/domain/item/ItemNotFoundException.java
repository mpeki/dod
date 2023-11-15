package dk.dodgame.domain.item;

import java.io.Serial;

public class ItemNotFoundException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = -4895912429655192954L;

  public ItemNotFoundException() {
    super();
  }

  public ItemNotFoundException(String message) {
    super(message);
  }
}
