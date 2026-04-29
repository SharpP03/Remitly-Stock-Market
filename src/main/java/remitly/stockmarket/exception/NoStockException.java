package remitly.stockmarket.exception;

public class NoStockException extends RuntimeException {
    public NoStockException(String stockName) {
        super("Stock not found: " + stockName);
    }
}
