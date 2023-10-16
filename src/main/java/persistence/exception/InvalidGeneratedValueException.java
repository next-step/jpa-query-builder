package persistence.exception;

public class InvalidGeneratedValueException extends IllegalArgumentException {

    public InvalidGeneratedValueException() {
        super("지원하지 않는 GeneratedValue 입니다.");
    }

    public InvalidGeneratedValueException(String s) {
        super(s);
    }
}
