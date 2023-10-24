package persistence.exception;

import jakarta.persistence.PersistenceException;

public class NotMappingEntityClassException extends PersistenceException {

    public NotMappingEntityClassException() {
        super("not have default constructor");
    }

    public NotMappingEntityClassException(String message, Throwable cause) {
        super(message, cause);
    }
}
