package orm.exception;

public class IdValueRequiredException extends OrmPersistenceException {

    public IdValueRequiredException(String message) {
        super(message);
    }

    public IdValueRequiredException(String message, Throwable cause) {
        super(message, cause);
    }
}
