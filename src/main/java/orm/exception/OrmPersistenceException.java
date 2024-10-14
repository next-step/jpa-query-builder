package orm.exception;

public class OrmPersistenceException extends RuntimeException {

    public OrmPersistenceException(String message) {
        super(message);
    }

    public OrmPersistenceException(String message, Throwable cause) {
        super(message, cause);
    }

}
