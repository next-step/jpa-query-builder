package persistence.sql.model;

public class NotEntityException extends RuntimeException{

    private static final String ERROR_MESSAGE = "해당 클래스는 Entity가 아닙니다.";

    public NotEntityException() {
        super(ERROR_MESSAGE);
    }
}
