package persistence.exception;

import jakarta.persistence.PersistenceException;

public class NotEqualEntityTypeException extends PersistenceException {

    private static final String MESSAGE = "Entity %s is not equal type to %s";

    public NotEqualEntityTypeException(Class<?> entityType, Class<?> entity) {
        super(String.format(MESSAGE, entityType.getName(), entity.getName()));
    }
}
