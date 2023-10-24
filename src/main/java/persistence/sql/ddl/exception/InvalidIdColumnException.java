package persistence.sql.ddl.exception;

public class InvalidIdColumnException extends RuntimeException {

    public InvalidIdColumnException() {
        super("유효하지 않은 id 컬럼입니다.");
    }

    public InvalidIdColumnException(final String message) {
        super(message);
    }
}
