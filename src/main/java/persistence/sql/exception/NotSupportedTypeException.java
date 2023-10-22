package persistence.sql.exception;

public class NotSupportedTypeException extends RuntimeException {

    public NotSupportedTypeException() {
        super();
    }

    public NotSupportedTypeException(String message) {
        super(message);
    }

    public NotSupportedTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotSupportedTypeException(Throwable cause) {
        super(cause);
    }

    protected NotSupportedTypeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
