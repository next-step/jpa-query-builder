package persistence.sql.dialect;

public class UnsupportedClassTypeException extends RuntimeException {

    private static final String ERROR_MESSAGE = "지원되지 않는 클래스 타입입니다.";

    public UnsupportedClassTypeException() {
        super(ERROR_MESSAGE);
    }
}
