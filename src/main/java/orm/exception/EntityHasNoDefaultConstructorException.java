package orm.exception;

public class EntityHasNoDefaultConstructorException extends OrmPersistenceException {

    public EntityHasNoDefaultConstructorException(String message) {
        super(message);
    }

    public EntityHasNoDefaultConstructorException(String message, Throwable cause) {
        super(message, cause);
    }
}
