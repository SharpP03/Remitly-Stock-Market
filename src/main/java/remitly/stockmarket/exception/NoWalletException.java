package remitly.stockmarket.exception;

public class NoWalletException extends RuntimeException {
    public NoWalletException(String message) {
        super(message);
    }
}
