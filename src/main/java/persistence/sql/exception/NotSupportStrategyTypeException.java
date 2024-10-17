package persistence.sql.exception;

public class NotSupportStrategyTypeException extends IllegalArgumentException {
    public NotSupportStrategyTypeException(ExceptionMessage exceptionMessage) {
        super(exceptionMessage.getMessage());
    }
}
