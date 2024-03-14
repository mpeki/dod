package dk.dodgame.util.item;

import java.io.Serial;

public class PaymentException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 4508178023197278220L;

    public PaymentException(String message){
        super(message);
    }

    public PaymentException(String message, String cause){
        super(message, new Throwable(cause));
    }

}
