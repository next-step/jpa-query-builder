package persistence.sql.dialect.exception;

public class InvalidConstantTypeException extends RuntimeException {

    private static final String MESSAGE = "지원하지 않는 제약조건 값이 들어왔습니다.";

    public InvalidConstantTypeException() {
        super(MESSAGE);
    }

}
