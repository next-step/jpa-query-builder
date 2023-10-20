package persistence.exception;

public class FiledEmptyException extends IllegalArgumentException {
    private final static String DUALIST_MESSAGE = "필드가 비어있습니다.";

    public FiledEmptyException(String message) {
        super(message);
    }

    public FiledEmptyException() {
        super(DUALIST_MESSAGE);
    }
}
