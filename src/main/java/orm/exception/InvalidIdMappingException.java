package orm.exception;

public class InvalidIdMappingException extends OrmPersistenceException {

    public InvalidIdMappingException(String message) {
        super(message);
    }

    public InvalidIdMappingException(String message, Throwable cause) {
        super(message, cause);
    }
}
