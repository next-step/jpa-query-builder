package persistence.sql.ddl.exception;

public class FieldNotEnoughException extends RuntimeException {
    public FieldNotEnoughException(String message) {
        super(message);
    }
}
