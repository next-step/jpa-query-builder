package persistence.sql.dml.exception;

public class NotFoundFieldException extends RuntimeException{
    private static final String MESSAGE = "필드의 이름을 찾지 못했습니다.";

    public NotFoundFieldException() {
        super(MESSAGE);
    }
}
