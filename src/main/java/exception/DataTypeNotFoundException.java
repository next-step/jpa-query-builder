package exception;

public class DataTypeNotFoundException extends RuntimeException {
    public DataTypeNotFoundException() {
        super("데이터 타입을 찾을 수 없습니다.");
    }

    public DataTypeNotFoundException(final String message) {
        super(message);
    }
}
