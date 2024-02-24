package persistence.sql.domain;

import jakarta.persistence.Column;

import java.lang.reflect.Field;

public class ColumnName {

    private final String name;

    public ColumnName(Field field) {
        Column annotation = field.getAnnotation(Column.class);
        if (annotation != null && annotation.name().length() > 0) {
            this.name = annotation.name();
            return;
        }
        this.name = field.getName();
    }

    public String getName() {
        return name;
    }
}
