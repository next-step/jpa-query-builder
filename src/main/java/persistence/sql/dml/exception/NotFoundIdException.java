package persistence.sql.dml.exception;

public class NotFoundIdException extends RuntimeException{
    private static final String MESSAGE = "아이디를 찾을 수 없습니다.";

    public NotFoundIdException() {
        super(MESSAGE);
    }
}
