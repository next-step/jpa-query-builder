package persistence.exception;

public class InvalidGeneratedValue extends IllegalArgumentException {

    public InvalidGeneratedValue() {
        super("지원하지 않는 GeneratedValue 입니다.");
    }

    public InvalidGeneratedValue(String s) {
        super(s);
    }
}
