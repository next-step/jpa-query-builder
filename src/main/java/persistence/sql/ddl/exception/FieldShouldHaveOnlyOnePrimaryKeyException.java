package persistence.sql.ddl.exception;

public class FieldShouldHaveOnlyOnePrimaryKeyException extends RuntimeException {
    public FieldShouldHaveOnlyOnePrimaryKeyException(String message) {
        super(message);
    }
}
