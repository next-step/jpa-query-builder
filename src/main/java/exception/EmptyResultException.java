package exception;

public class EmptyResultException extends RuntimeException {

    public EmptyResultException() {
        super("데이터가 존재하지 않습니다.");
    }

    public EmptyResultException(final String message) {
        super(message);
    }
}
