package persistence.exception;

import jakarta.persistence.PersistenceException;

public class NotFoundEntityException extends PersistenceException {

    private static final String MESSAGE_FORMAT = "Entity Class %s not found";

    public NotFoundEntityException(Class<?> entity) {
        super(MESSAGE_FORMAT.replace("%s", entity.getName()));
    }

    public NotFoundEntityException(String message) {
        super(message);
    }
}
