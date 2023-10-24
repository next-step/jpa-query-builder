package persistence.exception;

public class NoSuchEntityFoundException extends RuntimeException {

    public NoSuchEntityFoundException() {
        super("해당하는 엔티티를 조회할 수 없습니다.");
    }
}
