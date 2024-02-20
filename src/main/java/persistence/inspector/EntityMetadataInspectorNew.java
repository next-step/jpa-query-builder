package persistence.inspector;

import jakarta.persistence.*;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EntityMetadataInspectorNew {

    public String getTableName(Class<?> clazz) {
        if (clazz.isAnnotationPresent(Table.class) && !clazz.getAnnotation(Table.class).name().isBlank()) {
            return clazz.getAnnotation(Table.class).name();
        }
        return clazz.getSimpleName().toLowerCase();
    }

    public List<EntityColumn> getEntityColumns(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(this::isPersistable)
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

    public List<EntityColumn> getPrimaryKeys(Class<?> clazz) {
        return getEntityColumns(clazz).stream().filter(EntityColumn::isPrimaryKey).collect(Collectors.toList());
    }

    public boolean isPersistable(Field field) {
        return !field.isAnnotationPresent(Transient.class);
    }

    public String getColumnName(Field field) {
        return field.isAnnotationPresent(Column.class) && !field.getAnnotation(Column.class).name().isBlank() ?
                field.getAnnotation(Column.class).name() : field.getName();
    }

    private EntityColumnType getColumnType(Field field) {
        return EntityColumnType.get(field.getType());
    }

    private boolean isNullable(Field field) {
        return !field.isAnnotationPresent(Column.class) || field.getAnnotation(Column.class).nullable();
    }

    private boolean isPrimaryKey(Field field) {
        return field.isAnnotationPresent(Id.class);
    }

    public boolean isAutoIncrement(Field field) {
        return field.isAnnotationPresent(GeneratedValue.class);
    }

    public List<Field> getFields(Class<?> clazz) throws Exception {
        return Arrays.asList(clazz.getDeclaredFields());
    }

    public Field getField(Class<?> clazz, String fieldName) throws Exception {
        return clazz.getDeclaredField(fieldName);
    }


}
