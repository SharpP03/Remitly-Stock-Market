package remitly.stockmarket.exception;

public class NoWalletException extends RuntimeException {
    public NoWalletException(Long walletId) {
        super("Wallet not found: " + walletId);
    }

    public NoWalletException(String message) {
        super(message);
    }
}
