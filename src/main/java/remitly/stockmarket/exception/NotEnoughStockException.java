package remitly.stockmarket.exception;

public class NotEnoughStockException extends RuntimeException {
    public NotEnoughStockException() {
        super("Not enough stock quantity to sell");
    }
}
