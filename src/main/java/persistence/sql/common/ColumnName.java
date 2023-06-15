package persistence.sql.common;

import jakarta.persistence.Column;

import java.lang.reflect.Field;

public class ColumnName {
    private final Field field;

    public ColumnName(Field field) {this.field = field;}

    @Override
    public String toString() {
        final Column column = field.getDeclaredAnnotation(Column.class);
        final String name = column == null || column.name().isBlank()
                ? field.getName()
                : column.name();
        return name.toLowerCase();
    }
}
