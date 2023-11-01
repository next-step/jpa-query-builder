package exception;

public class InvalidEntityException extends RuntimeException {

    public InvalidEntityException(final String message) {
        super(message);
    }

    public InvalidEntityException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public InvalidEntityException(final Throwable cause) {
        super(cause);
    }
}
