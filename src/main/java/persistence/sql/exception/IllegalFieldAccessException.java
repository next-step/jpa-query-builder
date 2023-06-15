package persistence.sql.exception;

public class IllegalFieldAccessException extends RuntimeException {
    private static final String MESSAGE = "필드의 Value 를 가져오는데 실패했습니다.";

    public IllegalFieldAccessException() {
        super(MESSAGE);
    }
}
