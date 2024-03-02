package persistence.sql;

import jakarta.persistence.Id;
import jakarta.persistence.Transient;

import java.lang.reflect.Field;

public class ColumnUtils {

    private ColumnUtils() {
    }

    public static String name(Field field) {
        final jakarta.persistence.Column columnAnnotation = field.getAnnotation(jakarta.persistence.Column.class);

        if (existColumnNameProperty(columnAnnotation)) {
            return columnAnnotation.name();
        }
        return field.getName();
    }

    private static boolean existColumnNameProperty(jakarta.persistence.Column columnAnnotation) {
        return columnAnnotation != null && !columnAnnotation.name().isEmpty();
    }

    public static boolean includeColumn(Field field) {
        return !field.isAnnotationPresent(Transient.class);
    }

    public static boolean isId(Field field) {
        return field.isAnnotationPresent(Id.class);
    }

}
