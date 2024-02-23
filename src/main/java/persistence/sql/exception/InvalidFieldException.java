package persistence.sql.exception;

public class InvalidFieldException extends IllegalArgumentException {
    public InvalidFieldException() {
        super("유효하지 않은 필드입니다.");
    }
}
