package persistence.sql.ddl;

public class ExceptionUtil {

    public static final String CLASS_NULL_MESSAGE = "class가 존재하지 않습니다.";
    public static final String OBJECT_NULL_MESSAGE = "Object가 존재하지 않습니다.";

    public static void requireNonNull(Class<?> clazz) {
        if (clazz == null) {
            throw new IllegalArgumentException(CLASS_NULL_MESSAGE);
        }
    }

    public static void requireNonNull(Object object) {
        if (object == null) {
            throw new IllegalArgumentException(OBJECT_NULL_MESSAGE);
        }
    }

}
