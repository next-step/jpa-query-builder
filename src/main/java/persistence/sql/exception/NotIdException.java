package persistence.sql.exception;

public class NotIdException extends IllegalArgumentException {
    public NotIdException() {
        super("유효하지 않은 ID입니다.");
    }
}
