package persistence.model.exception;

public class ColumnNotFoundException extends RuntimeException {
    public ColumnNotFoundException(String columnName) {
        super("COLUMN NOT FOUND FOR NAME : " + columnName);
    }
}
