package persistence.sql.exception;

public class IncorrectGenerationTypeException extends RuntimeException {
    public IncorrectGenerationTypeException(ExceptionMessage exceptionMessage) {
        super(exceptionMessage.getMessage());
    }
}
