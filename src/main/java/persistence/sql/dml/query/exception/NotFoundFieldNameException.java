package persistence.sql.dml.query.exception;

public class NotFoundFieldNameException extends RuntimeException{
    private static final String MESSAGE = "필드이름을 찾지 못했습니다.";

    public NotFoundFieldNameException() {
        super(MESSAGE);
    }
}
