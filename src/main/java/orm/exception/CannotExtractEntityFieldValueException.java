package orm.exception;

public class CannotExtractEntityFieldValueException extends OrmPersistenceException {

    public CannotExtractEntityFieldValueException(String message) {
        super(message);
    }

    public CannotExtractEntityFieldValueException(String message, Throwable cause) {
        super(message, cause);
    }
}
