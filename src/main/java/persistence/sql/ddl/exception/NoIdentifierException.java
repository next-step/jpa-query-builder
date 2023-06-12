package persistence.sql.ddl.exception;

public class NoIdentifierException extends RuntimeException {

    private static final String MESSAGE = "No identifier specified for entity: %s";

    public NoIdentifierException(String entityClassName) {
        super(String.format(MESSAGE, entityClassName));
    }
}
