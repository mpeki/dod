package dk.pekilidi.dod.items;

import java.io.Serial;

public class InsufficientFundsException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = -3011032623160131073L;

  public InsufficientFundsException() {
    super();
  }

  public InsufficientFundsException(String message) {
    super(message);
  }
}
