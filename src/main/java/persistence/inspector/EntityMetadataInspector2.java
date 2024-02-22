package persistence.inspector;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EntityMetadataInspector2 {

    public static String getTableName(Class<?> clazz) {
        if (clazz.isAnnotationPresent(Table.class) && !clazz.getAnnotation(Table.class).name().isBlank()) {
            return clazz.getAnnotation(Table.class).name();
        }
        return clazz.getSimpleName().toLowerCase();
    }

    public static List<EntityColumn> getEntityColumns(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(EntityMetadataInspector2::isPersistable)
                .map(field -> new EntityColumn(
                        field.getName(),
                        getColumnName(field),
                        getColumnType(field),
                        isPrimaryKey(field),
                        isNullable(field),
                        isAutoIncrement(field)
                ))
                .collect(Collectors.toList());
    }

    public static List<Field> getIdFields(Class<?> clazz) {
        return getFields(clazz).stream().filter(EntityMetadataInspector2::isPrimaryKey).collect(Collectors.toList());
    }

    public static Field getIdField(Class<?> clazz) {
        return getFields(clazz).stream().filter(EntityMetadataInspector2::isPrimaryKey).findFirst().orElse(null);
    }

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

    public static List<Field> getFields(Class<?> clazz) {
        return Arrays.asList(clazz.getDeclaredFields());
    }

}
