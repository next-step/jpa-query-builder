package persistence.exception;

public class NotEntityException extends RuntimeException {

    public NotEntityException() {
        super("@Entity Annotation is required for class to create ddl");
    }
}
