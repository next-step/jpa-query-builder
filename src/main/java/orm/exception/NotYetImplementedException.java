package orm.exception;

public class NotYetImplementedException extends OrmPersistenceException {

    public NotYetImplementedException(String message) {
        super(message);
    }

    public NotYetImplementedException(String message, Throwable cause) {
        super(message, cause);
    }
}
