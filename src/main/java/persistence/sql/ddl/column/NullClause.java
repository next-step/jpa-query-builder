package persistence.sql.ddl.column;

import jakarta.persistence.Column;

import java.lang.reflect.Field;

public class NullClause {
    private static final String NULL = "NULL";
    private static final String NOT_NULL = "NOT NULL";
    private final Field field;

    public NullClause(Field field) {
        this.field = field;
    }

    public String getQuery() {
        Column column = field.getAnnotation(Column.class);
        if (column == null) {
            return NULL;
        }
        boolean isNullable = column.nullable();
        if (isNullable) {
            return NULL;
        }
        return NOT_NULL;
    }
}
