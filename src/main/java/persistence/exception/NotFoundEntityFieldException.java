package persistence.exception;

import jakarta.persistence.PersistenceException;

public class NotFoundEntityFieldException extends PersistenceException {

    private static final String MESSAGE = "Field %s not found in entity %s";

    public NotFoundEntityFieldException(String fieldName, Class<?> entityType) {
        super(String.format(MESSAGE, fieldName, entityType.getName()));
    }
}
