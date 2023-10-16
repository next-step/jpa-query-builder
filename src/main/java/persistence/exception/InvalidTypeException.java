package persistence.exception;

public class InvalidTypeException extends IllegalArgumentException {

    public InvalidTypeException() {
        super("유효하지 않은 자료형입니다.");
    }

    public InvalidTypeException(String s) {
        super(s);
    }
}
