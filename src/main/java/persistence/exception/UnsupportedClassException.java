package persistence.exception;

public class UnsupportedClassException extends RuntimeException {

    public UnsupportedClassException(Class<?> clazz) {
        super("Unsupported class: " + clazz.getName());
    }
}
