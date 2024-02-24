package persistence.sql.dml.exception;

public class InvalidFieldValueException extends RuntimeException{
    private static final String MESSAGE = "필드 값이 올바르지 않습니다.";

    public InvalidFieldValueException() {
        super(MESSAGE);
    }
}
