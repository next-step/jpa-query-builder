package persistence.exception;

public class PersistenceException extends RuntimeException {
    public PersistenceException() {
        super();
    }

    public PersistenceException(final String message) {
        super(message);
    }

    public PersistenceException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public PersistenceException(final Throwable cause) {
        super(cause);
    }

}
