package persistence.exception;

public class UnknownException extends RuntimeException {

    private static final String MESSAGE_PREFIX = "Unknown ";

    public UnknownException(String message) {
        super(MESSAGE_PREFIX + message);
    }

}
