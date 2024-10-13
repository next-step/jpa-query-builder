package persistence.sql.ddl;

public class AnnotationUtils {
    public static <T> Object getDefaultValue(Class<T> clazz, String name) {
        try {
            return clazz.getMethod(name)
                    .getDefaultValue();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
