package persistence.exception;

public class NotSupportTypeException extends IllegalArgumentException {
    private final static String DEFAULT_MESSAGE = "지원하지 않는 타입입니다.";
    public NotSupportTypeException() {
        super(DEFAULT_MESSAGE);
    }
    public NotSupportTypeException(String message) {
        super(message);
    }
}
