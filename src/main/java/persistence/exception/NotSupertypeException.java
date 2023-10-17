package persistence.exception;

public class NotSupertypeException extends IllegalArgumentException {
    private final static String DEFAULT_MESSAGE = "지원하지 않는 타입입니다.";
    public NotSupertypeException() {
        super(DEFAULT_MESSAGE);
    }
}
