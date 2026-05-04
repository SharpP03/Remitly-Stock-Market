package remitly.stockmarket.exception;

public class NoBankStockException extends RuntimeException {
    public NoBankStockException(String stockName) {
        super("No stock in bank: " + stockName);
    }
}
