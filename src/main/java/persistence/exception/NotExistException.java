package persistence.exception;

public class NotExistException extends RuntimeException {

    private static final String MESSAGE_PREFIX = "Not exist ";

    public NotExistException(String message) {
        super(MESSAGE_PREFIX + message);
    }

    public NotExistException(String message, Throwable cause) {
        super(MESSAGE_PREFIX + message, cause);
    }

}
