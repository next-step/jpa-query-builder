package persistence.sql.ddl.clause;

import jakarta.persistence.Transient;
import persistence.sql.ddl.dialect.Dialect;
import persistence.sql.meta.column.ColumnName;
import persistence.sql.meta.column.ColumnType;
import persistence.sql.meta.column.Nullable;

import java.lang.reflect.Field;

public class ColumnClause {
    private final ColumnName columnName;
    private final Nullable nullable;
    private final ColumnType columnType;

    public ColumnClause(final Field field) {
        this.validateColumn(field);
        this.columnName = new ColumnName(field);
        this.nullable = Nullable.getNullable(field);
        this.columnType = new ColumnType(field);
    }

    public String getSQL(Dialect dialect) {
        return String.format("%s %s %s",
                columnName.getName(),
                dialect.getColumnDataType(columnType),
                dialect.getColumnNullableType(nullable));
    }

    private void validateColumn(Field field) {
        if (field.isAnnotationPresent(Transient.class)) {
            throw new IllegalArgumentException(String.format("%s has Transient Annotation Can not Field", field.getName()));
        }
    }
}
