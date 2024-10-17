package persistence.sql.exception;

public class RequiredObjectException extends IllegalArgumentException {
    public RequiredObjectException(ExceptionMessage exceptionMessage) {
        super(exceptionMessage.getMessage());
    }
}
