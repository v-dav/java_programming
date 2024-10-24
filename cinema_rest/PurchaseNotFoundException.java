package cinema;

public class PurchaseNotFoundException extends RuntimeException {
    public PurchaseNotFoundException(String message) {
        super(message);
    }
}