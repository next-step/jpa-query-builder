package persistence.sql;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.lang.reflect.Field;

public class MetadataUtils {
    private final Class<?> clazz;

    public MetadataUtils(Class<?> clazz) {
        this.clazz = clazz;
    }

    public String getTableName() {
        Table annotation = clazz.getAnnotation(Table.class);
        if (annotation == null) {
            return clazz.getSimpleName().toLowerCase();
        }

        if (!annotation.name().isEmpty()) {
            return annotation.name();
        }

        return clazz.getSimpleName().toLowerCase();
    }

    public String getIdField() {
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class)) {
                return field.getName();
            }
        }
        throw new IllegalArgumentException("@Id 어노테이션이 존재하지 않음");
    }

    public String getFieldName(Field field) {
        Column annotation = field.getAnnotation(Column.class);
        if (annotation == null) {
            return field.getName();
        }
        if (!annotation.name().isEmpty()) {
            return annotation.name();
        }

        return field.getName();
    }
}
