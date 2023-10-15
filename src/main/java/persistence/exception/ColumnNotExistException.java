package persistence.exception;

import jakarta.persistence.PersistenceException;

public class ColumnNotExistException extends PersistenceException {
    public ColumnNotExistException() {
        super("컬럼이 존재하지 않습니다.");
    }

    public ColumnNotExistException(final String message) {
        super(message);
    }

    public ColumnNotExistException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
