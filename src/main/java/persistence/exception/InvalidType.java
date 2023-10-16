package persistence.exception;

public class InvalidType extends IllegalArgumentException {

    public InvalidType() {
        super("유효하지 않은 자료형입니다.");
    }

    public InvalidType(String s) {
        super(s);
    }
}
