package persistence.sql.ddl.exception;

public class UnrecognizedGeneratedValueException extends RuntimeException {

    public UnrecognizedGeneratedValueException() {
        super();
    }

    public UnrecognizedGeneratedValueException(String message) {
        super(message);
    }

    public UnrecognizedGeneratedValueException(String message, Throwable cause) {
        super(message, cause);
    }
}
