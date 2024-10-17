package persistence.sql.exception;

public class RequiredIdException extends IllegalArgumentException {

    public RequiredIdException(ExceptionMessage exceptionMessage) {
        super(exceptionMessage.getMessage());
    }
}
