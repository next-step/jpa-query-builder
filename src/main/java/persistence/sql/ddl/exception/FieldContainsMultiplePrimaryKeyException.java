package persistence.sql.ddl.exception;

public class FieldContainsMultiplePrimaryKeyException extends RuntimeException {
    public FieldContainsMultiplePrimaryKeyException(String message) {
        super(message);
    }
}
