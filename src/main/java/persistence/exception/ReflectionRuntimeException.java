package persistence.exception;

public class ReflectionRuntimeException extends RuntimeException {
    public ReflectionRuntimeException(Class<?> clazz, Exception e) {
        super("Reflection error on class: " + clazz.getName(), e);
    }
}
