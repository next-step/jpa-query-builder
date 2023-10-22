package persistence.sql.exception;

public class PreconditionRequiredException extends RuntimeException {

    public PreconditionRequiredException() {
    }

    public PreconditionRequiredException(String message) {
        super(message);
    }

    public PreconditionRequiredException(String message, Throwable cause) {
        super(message, cause);
    }
}
