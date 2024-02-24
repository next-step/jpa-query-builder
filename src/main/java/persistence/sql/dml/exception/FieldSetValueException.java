package persistence.sql.dml.exception;

public class FieldSetValueException extends RuntimeException{
    private static final String MESSAGE = "필드 값 주입에 실패하였습니다.";

    public FieldSetValueException() {
        super(MESSAGE);
    }
}
