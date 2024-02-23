package persistence.sql.dml.caluse;

import jakarta.persistence.Transient;
import persistence.sql.meta.column.Column;

import java.lang.reflect.Field;

public class ColumnClause {
    private final Field field;

    public ColumnClause(Field field) {
        this.field = field;
    }

    public String getColumn() {
        if (field.isAnnotationPresent(Transient.class)) {
            return "";
        }
        return new Column(field).getColumnName();
    }
}
