package persistence.inspector;

import jakarta.persistence.*;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EntityMetadataInspector<T> {

    public final Class<T> clazz;

    public EntityMetadataInspector(Class<T> clazz) {
        this.clazz = clazz;
    }

    public String getTableName() {
        if (clazz.isAnnotationPresent(Table.class) && !clazz.getAnnotation(Table.class).name().isBlank()) {
            return clazz.getAnnotation(Table.class).name();
        }
        return clazz.getSimpleName().toLowerCase();
    }

    public List<EntityColumn> getEntityColumns() {
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

    public List<EntityColumn> getPrimaryKeys() {
        return getEntityColumns().stream().filter(EntityColumn::isPrimaryKey).collect(Collectors.toList());
    }

    private boolean isPersistable(Field field) {
        return !field.isAnnotationPresent(Transient.class);
    }

    private String getColumnName(Field field) {
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

    private boolean isAutoIncrement(Field field) {
        return field.isAnnotationPresent(GeneratedValue.class);
    }

    public Field getField(String fieldName) throws Exception {
        return clazz.getDeclaredField(fieldName);
    }


}
