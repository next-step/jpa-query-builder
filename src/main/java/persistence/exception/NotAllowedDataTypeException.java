package persistence.exception;

public class NotAllowedDataTypeException extends RuntimeException{

    public NotAllowedDataTypeException() {
        super("DataType is NotAllowed");
    }
}
