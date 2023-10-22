package persistence.exception;

public class FieldEmptyException extends IllegalArgumentException {
    private final static String DUALIST_MESSAGE = "필드가 비어있습니다.";

    public FieldEmptyException(String message) {
        super(message);
    }

    public FieldEmptyException() {
        super(DUALIST_MESSAGE);
    }
}
