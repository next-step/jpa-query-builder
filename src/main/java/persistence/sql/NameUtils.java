package persistence.sql;

import jakarta.persistence.Column;
import jakarta.persistence.Table;

import java.lang.reflect.Field;

public class NameUtils {

    public static String getColumnName(Field field) {
        if (field.isAnnotationPresent(Column.class)
                && !"".equals(field.getAnnotation(Column.class).name())) {
            return field.getAnnotation(Column.class).name();
        }
        return field.getName();
    }

    public static String getTableName(Class<?> entityClass) {
        if (entityClass.isAnnotationPresent(Table.class)
                && !"".equals(entityClass.getAnnotation(Table.class).name())) {
            return entityClass.getAnnotation(Table.class).name();
        }
        return entityClass.getSimpleName();
    }
}
