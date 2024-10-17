package persistence.sql.exception;

public class RequiredClassException extends IllegalArgumentException {

    public RequiredClassException(ExceptionMessage exceptionMessage) {
        super(exceptionMessage.getMessage());
    }

}
