package persistence.sql.exception;

public class RequiredFieldException extends IllegalArgumentException {

    public RequiredFieldException(ExceptionMessage exceptionMessage) {
        super(exceptionMessage.getMessage());
    }
}
