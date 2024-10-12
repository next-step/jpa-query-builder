package persistence.sql.ddl.exception;

public class NotEntityException extends RuntimeException{
    public NotEntityException() {
    }

    public NotEntityException(String message) {
        super(message);
    }

    public NotEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotEntityException(Throwable cause) {
        super(cause);
    }

    public NotEntityException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
