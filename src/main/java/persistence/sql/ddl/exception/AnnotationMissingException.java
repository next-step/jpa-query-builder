package persistence.sql.ddl.exception;

public class AnnotationMissingException extends RuntimeException {
    public AnnotationMissingException(String message) {
        super(message);
    }
}
