package persistence.exception;

public class NotFoundException extends IllegalStateException {

    public NotFoundException(Throwable cause) {
        super(cause);
    }

    public NotFoundException(String message) {
        super(message);
    }
}
