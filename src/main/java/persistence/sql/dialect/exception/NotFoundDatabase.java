package persistence.sql.dialect.exception;

public class NotFoundDatabase extends RuntimeException {

    private static final String MESSAGE = "찾을 수 없는 데이터베이스입니다.";

    public NotFoundDatabase() {
        super(MESSAGE);
    }
}
