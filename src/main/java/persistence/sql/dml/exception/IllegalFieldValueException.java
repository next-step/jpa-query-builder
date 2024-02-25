package persistence.sql.dml.exception;

public class IllegalFieldValueException extends RuntimeException{
    private static final String MESSAGE = "적절하지 않은 필드 값 입니다.";

    public IllegalFieldValueException() {
        super(MESSAGE);
    }
}
