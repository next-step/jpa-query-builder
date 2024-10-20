package persistence.sql.exception;

public class RequiredDefaultConstructorException extends IllegalArgumentException {

    public RequiredDefaultConstructorException(ExceptionMessage exceptionMessage) {
        super(exceptionMessage.getMessage());
    }

}
