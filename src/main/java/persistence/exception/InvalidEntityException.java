package persistence.exception;

public class InvalidEntityException extends NullPointerException {

    public InvalidEntityException() {
        super("@Entity가 존재하지 않는 객체입니다.");
    }

    public InvalidEntityException(String s) {
        super(s);
    }
}
