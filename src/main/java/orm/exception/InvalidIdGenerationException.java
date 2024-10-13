package orm.exception;

public class InvalidIdGenerationException extends OrmPersistenceException {

    public InvalidIdGenerationException(String message) {
        super(message);
    }

    public InvalidIdGenerationException(String message, Throwable cause) {
        super(message, cause);
    }
}
