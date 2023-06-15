package persistence.sql.exception;

public class IdNameNotFoundException extends RuntimeException {
    private static final String MESSAGE = "Id 의 칼럼명을 가져오는데 실패했습니다.";

    public IdNameNotFoundException() {
        super(MESSAGE);
    }
}
