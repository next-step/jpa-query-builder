package persistence.sql.ddl.exception;

public class IncorrectIdFieldException extends RuntimeException {
    public IncorrectIdFieldException() {
    }

    public IncorrectIdFieldException(String message) {
        super(message);
    }

    public IncorrectIdFieldException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectIdFieldException(Throwable cause) {
        super(cause);
    }

    public IncorrectIdFieldException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
