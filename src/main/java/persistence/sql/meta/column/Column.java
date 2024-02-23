package persistence.sql.meta.column;

import jakarta.persistence.Transient;

import java.lang.reflect.Field;

public class Column {
    private final ColumnName columnName;

    public Column(final Field field) {
        this.validateColumn(field);
        this.columnName = new ColumnName(field);
    }

    public String getColumnName() {
        return columnName.getName();
    }

    private void validateColumn(Field field) {
        if (field.isAnnotationPresent(Transient.class)) {
            throw new IllegalArgumentException(String.format("%s has Transient Annotation Can not Field", field.getName()));
        }
    }
}
