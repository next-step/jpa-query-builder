package persistence.exception;

import jakarta.persistence.PersistenceException;

public class NotHavePrimaryKeyException extends PersistenceException {

    private static final String MESSAGE = "not have primary key";

    public NotHavePrimaryKeyException() {
        super(MESSAGE);
    }
}
