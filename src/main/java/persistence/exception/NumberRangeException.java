package persistence.exception;

public class NumberRangeException extends IllegalArgumentException {
    public NumberRangeException(String message) {
        super(message);
    }
}
