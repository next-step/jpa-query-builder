package persistence.inspector;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;

import java.lang.reflect.Field;

public class EntityFieldInspector {

    public static boolean isPersistable(Field field) {
        return !field.isAnnotationPresent(Transient.class);
    }

    public static String getColumnName(Field field) {
        return field.isAnnotationPresent(Column.class) && !field.getAnnotation(Column.class).name().isBlank() ?
                field.getAnnotation(Column.class).name() : field.getName();
    }

    public static EntityColumnType getColumnType(Field field) {
        return EntityColumnType.get(field.getType());
    }

    public static boolean isNullable(Field field) {
        return !field.isAnnotationPresent(Column.class) || field.getAnnotation(Column.class).nullable();
    }

    public static boolean isPrimaryKey(Field field) {
        return field.isAnnotationPresent(Id.class);
    }

    public static boolean isAutoIncrement(Field field) {
        return field.isAnnotationPresent(GeneratedValue.class);
    }

}
