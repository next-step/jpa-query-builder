package persistence.sql.exception;

public class NotSupportedIdException extends IllegalArgumentException {
    public NotSupportedIdException() {
        super("지원하지 않는 Id 유형입니다.");
    }
}
