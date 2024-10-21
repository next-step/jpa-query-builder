package persistence.model.exception;

public class ColumnInvalidException extends RuntimeException {
    public ColumnInvalidException(String message) {
        super("COLUMN INVALID. message = " + message);
    }
}
