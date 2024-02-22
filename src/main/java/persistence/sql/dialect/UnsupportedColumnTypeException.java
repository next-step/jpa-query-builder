package persistence.sql.dialect;

public class UnsupportedColumnTypeException extends RuntimeException {

    private static final String ERROR_MESSAGE = "지원되지 않는 컬럼 타입입니다.";

    public UnsupportedColumnTypeException() {
        super(ERROR_MESSAGE);
    }
}
