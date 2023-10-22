package persistence.sql.exception;

public class AccessRequiredException extends RuntimeException {

    public AccessRequiredException() {
    }

    public AccessRequiredException(String message) {
        super(message);
    }

    public AccessRequiredException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccessRequiredException(Throwable cause) {
        super(cause);
    }

    public AccessRequiredException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
