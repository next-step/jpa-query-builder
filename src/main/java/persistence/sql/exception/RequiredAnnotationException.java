package persistence.sql.exception;

public class RequiredAnnotationException extends RuntimeException {

    public RequiredAnnotationException() {
        super();
    }

    public RequiredAnnotationException(String message) {
        super(message);
    }

    public RequiredAnnotationException(String message, Throwable cause) {
        super(message, cause);
    }
}
