package persistence.exception;

public class NoEntityException extends IllegalArgumentException {
    private final static String DUALIST_MESSAGE = "엔티티 클래스가 아닙니다.";
    public NoEntityException() {
        super(DUALIST_MESSAGE);
    }
}
