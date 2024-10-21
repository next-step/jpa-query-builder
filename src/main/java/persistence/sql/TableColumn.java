package persistence.sql;

import jakarta.persistence.Column;

import java.lang.reflect.Field;


public class TableColumn {
    private final Class<?> type;
    private final String name;
    private final boolean isNullable;
    private Object value;

    public TableColumn(Field column) {
        this.type = column.getType();
        this.name = setColumnName(column);
        this.isNullable = nullable(column);
    }

    public Class<?> type() {
        return type;
    }
    public String columnName() {
        return name;
    }

    public boolean isNotNullable() {
        return !isNullable;
    }

    private boolean nullable(Field field) {
        Column column = field.getAnnotation(Column.class);
        return column == null || column.nullable();
    }

    private String setColumnName(Field field) {
        if (field.isAnnotationPresent(Column.class)) {
            Column column = field.getAnnotation(Column.class);
            if (!column.name().isEmpty()) {
                return column.name();
            }
        }
        return field.getName();
    }
}
