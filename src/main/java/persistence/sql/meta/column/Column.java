package persistence.sql.meta.column;

import jakarta.persistence.Transient;

import java.lang.reflect.Field;

public class Column {
    private final ColumnName columnName;
    private final Nullable nullable;
    private final ColumnType columnType;

    public Column(final Field field) {
        this.validateColumn(field);
        this.columnName = new ColumnName(field);
        this.nullable = Nullable.getNullable(field);
        this.columnType = new ColumnType(field);
    }

    public String getColumnName() {
        return columnName.getName();
    }

    public Nullable getNullable() {
        return nullable;
    }

    public ColumnType getColumnType() {
        return columnType;
    }

    private void validateColumn(Field field) {
        if (field.isAnnotationPresent(Transient.class)) {
            throw new IllegalArgumentException(String.format("%s has Transient Annotation Can not Field", field.getName()));
        }
    }
}
