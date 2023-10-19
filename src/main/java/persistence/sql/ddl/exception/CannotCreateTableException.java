package persistence.sql.ddl.exception;

public class CannotCreateTableException extends RuntimeException {
    public CannotCreateTableException(String message) {
        super(message);
    }
}
