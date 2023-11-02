package exception;

public class IncorrectResultSizeException extends RuntimeException {
    public IncorrectResultSizeException() {
        super("데이터가 2개 이상 존재합니다.");
    }

    public IncorrectResultSizeException(final String message) {
        super(message);
    }
}
