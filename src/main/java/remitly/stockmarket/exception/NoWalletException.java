package remitly.stockmarket.exception;

public class NoWalletException extends RuntimeException {
    public NoWalletException(String walletId) {
        super("Wallet not found: " + walletId);
    }
}
