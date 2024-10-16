package persistence.sql.ddl.create.component;

import jakarta.persistence.Column;

import java.lang.reflect.Field;

public interface ComponentBuilder {
    String build();

    default String getNameFromField(Field field) {
        if (field.isAnnotationPresent(Column.class)
                && !"".equals(field.getAnnotation(Column.class).name())) {
            return field.getAnnotation(Column.class).name();
        }
        return field.getName();
    }
}
