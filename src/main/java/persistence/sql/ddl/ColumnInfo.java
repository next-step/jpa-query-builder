package persistence.sql.ddl;

import jakarta.persistence.Column;

import java.lang.reflect.Field;

public class ColumnInfo {
    private final Field field;

    public ColumnInfo(Field field) {
        this.field = field;
    }

    public String getColumnName() {
        if (!this.field.isAnnotationPresent(Column.class)) {
            return this.field.getName();
        }
        String columnName = this.field.getAnnotation(Column.class).name();
        if (columnName.isEmpty()) {
            return this.field.getName();
        }
        return columnName;
    }
}
