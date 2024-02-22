package persistence.sql.dialect;

public class UnsupportedGenerationTypeException extends RuntimeException {

    private static final String ERROR_MESSAGE = "지원되지 않는 기본 키 생성 타입입니다.";

    public UnsupportedGenerationTypeException() {
        super(ERROR_MESSAGE);
    }
}
