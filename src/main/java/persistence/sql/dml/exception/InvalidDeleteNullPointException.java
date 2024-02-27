package persistence.sql.dml.exception;

public class InvalidDeleteNullPointException extends RuntimeException{
    private static final String MESSAGE = "값이 존재하지 않아 삭제가 불가능합니다.";

    public InvalidDeleteNullPointException() {
        super(MESSAGE);
    }
}
