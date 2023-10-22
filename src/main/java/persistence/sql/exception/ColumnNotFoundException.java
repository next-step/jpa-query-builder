package persistence.sql.exception;

public class ColumnNotFoundException extends RuntimeException {

    public ColumnNotFoundException() {
    }

    public ColumnNotFoundException(String message) {
        super(message);
    }

    public ColumnNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
