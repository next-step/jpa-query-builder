package orm.exception;

public class InvalidQueryBuilderException extends OrmPersistenceException {

    public InvalidQueryBuilderException(String message) {
        super(message);
    }

    public InvalidQueryBuilderException(String message, Throwable cause) {
        super(message, cause);
    }
}
