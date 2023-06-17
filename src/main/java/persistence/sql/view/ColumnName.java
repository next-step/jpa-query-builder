package persistence.sql.view;

import jakarta.persistence.Column;

import java.lang.reflect.Field;

public class ColumnName {
    private final String name;

    public ColumnName(Field field) {
        final Column column = field.getDeclaredAnnotation(Column.class);
        final String name = column == null || column.name().isBlank()
                ? field.getName()
                : column.name();
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
