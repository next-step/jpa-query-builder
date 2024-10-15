package persistence.sql.ddl.exception;

public class UnknownException extends RuntimeException {

    private static final String MESSAGE_PREFIX = "Unknown ";

    public UnknownException(String message) {
        super(MESSAGE_PREFIX + message);
    }

    public UnknownException(String message, Throwable cause) {
        super(MESSAGE_PREFIX + message, cause);
    }

}
