package persistence.sql.column;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

import java.lang.reflect.Field;

public class JpaColumn {
    protected static final String SPACE = " ";
    private static final String EMPTY = "";
    private static final String NOT_NULL = "not null";

    protected String name;
    protected ColumnType columnType;
    private final String nullable;

    public static JpaColumn from(Field field) {
        ColumnType columnType = ColumnType.toDdl(field.getType());
        if (field.isAnnotationPresent(Column.class)) {
            String name = convertName(field);
            String nullable = getNullable(field);
            return new JpaColumn(name, nullable, columnType);
        }
        if (field.isAnnotationPresent(Id.class)) {
            return new PkColumn(field);
        }
        return new JpaColumn(field.getName(), columnType);
    }

    public JpaColumn(String name, ColumnType columnType) {
        this(name, EMPTY, columnType);
    }

    public JpaColumn(String name, String nullable, ColumnType columnType) {
        this.name = name;
        this.nullable = nullable;
        this.columnType = columnType;
    }

    private static String getNullable(Field field) {
        boolean isNullable = field.getAnnotation(Column.class).nullable();
        if (isNullable) {
            return EMPTY;
        }
        return SPACE + NOT_NULL;
    }

    private static String convertName(Field field) {
        if (!field.isAnnotationPresent(Column.class)) {
            return field.getName();
        }
        String columnName = field.getAnnotation(Column.class).name();
        if (columnName.isBlank() || columnName.isEmpty()) {
            return field.getName();
        }
        return columnName;
    }

    public String getDefinition() {
        return this.name + columnType.getColumnDefinition() + nullable;
    }

    public String getName() {
        return name;
    }
}
