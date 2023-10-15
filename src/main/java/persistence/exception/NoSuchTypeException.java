package persistence.exception;

import jakarta.persistence.PersistenceException;

public class NoSuchTypeException extends PersistenceException {
    public NoSuchTypeException() {
        super();
    }

    public NoSuchTypeException(final String message) {
        super(message);
    }

    public NoSuchTypeException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
