package orm.exception;

public class RowMapperException extends OrmPersistenceException {

    public RowMapperException(String message) {
        super(message);
    }

    public RowMapperException(String message, Throwable cause) {
        super(message, cause);
    }
}
