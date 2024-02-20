package persistence.sql.column;

import jakarta.persistence.Id;
import persistence.sql.dialect.Dialect;

import java.lang.reflect.Field;

public class JpaColumn implements Column {
    protected static final String SPACE = " ";
    private static final String EMPTY = "";
    private static final String NOT_NULL = "not null";

    private final String name;
    private final ColumnType mysqlColumn;
    private final String nullable;

    public static Column from(Field field, Dialect dialect) {
        ColumnType columnType = dialect.getColumn(field.getType());
        String name = field.getName();
        String nullable = EMPTY;

        if (field.isAnnotationPresent(jakarta.persistence.Column.class)) {
            boolean isNullable = field.getAnnotation(jakarta.persistence.Column.class).nullable();
            String columnName = field.getAnnotation(jakarta.persistence.Column.class).name();

            name = convertName(columnName, name);
            nullable = getNullable(isNullable);
        }
        if (field.isAnnotationPresent(Id.class)) {
            return new PkColumn(field, name, columnType);
        }
        return new JpaColumn(name, columnType, nullable);
    }

    private JpaColumn(String name, ColumnType mysqlColumn, String nullable) {
        this.name = name;
        this.mysqlColumn = mysqlColumn;
        this.nullable = nullable;
    }

    private static String getNullable(boolean isNullable) {
        if (isNullable) {
            return EMPTY;
        }
        return SPACE + NOT_NULL;
    }

    private static String convertName(String columnName, String fieldName) {
        if (columnName.isBlank() || columnName.isEmpty()) {
            return fieldName;
        }
        return columnName;
    }

    public String getDefinition() {
        return this.name + mysqlColumn.getColumnDefinition() + nullable;
    }

    public String getName() {
        return name;
    }
}
