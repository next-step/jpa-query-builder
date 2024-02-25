package persistence.sql.dml.caluse;

import jakarta.persistence.Transient;
import persistence.sql.meta.column.ColumnName;

import java.lang.reflect.Field;

public class ColumnClause {
    private final ColumnName columnName;

    public ColumnClause(Field field) {
        this.validateColumn(field);
        this.columnName = new ColumnName(field);
    }

    private void validateColumn(Field field) {
        if (field.isAnnotationPresent(Transient.class)) {
            throw new IllegalArgumentException(String.format("%s has Transient Annotation Can not Field", field.getName()));
        }
    }

    public String getColumnName() {
        return columnName.getName();
    }
}
