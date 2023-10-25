package persistence.exception;

import jakarta.persistence.PersistenceException;

public class NotFoundSqlTypeException extends PersistenceException {

    private static final String MESSAGE = "Sql type not found";

    public NotFoundSqlTypeException() {
        super(MESSAGE);
    }
}
