package persistence.sql.ddl.create.component;

import jakarta.persistence.Column;

import java.lang.reflect.Field;

public class ComponentUtils {

    public static String getNameFromField(Field field) {
        if (field.isAnnotationPresent(Column.class)
                && !"".equals(field.getAnnotation(Column.class).name())) {
            return field.getAnnotation(Column.class).name();
        }
        return field.getName();
    }
}
