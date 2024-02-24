package persistence.sql.dialect.exception;

public class InvalidJavaClassException extends RuntimeException {
    private static final String MESSAGE = "지원하지 않는 타입이 들어왔습니다.";

    public InvalidJavaClassException() {
        super(MESSAGE);
    }
}
