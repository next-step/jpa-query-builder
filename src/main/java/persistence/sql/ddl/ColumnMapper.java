package persistence.sql.ddl;

import jakarta.persistence.Column;

import java.lang.reflect.Field;

public class ColumnMapper {
    private static final String SPACE = " ";
    private static final String LENGTH_FORMAT = "%s(%d)";
    private static final String COLUMN_FORMAT = "%s %s";
    private static final String NOT_NULL = "not null";

    private static final Integer DEFAULT_LENGTH = 255;

    private final Dialect dialect;

    public ColumnMapper(Dialect dialect) {
        this.dialect = dialect;
    }

    String column(Field field) {
        Column column = field.getAnnotation(Column.class);
        String defaultColumn = String.format(COLUMN_FORMAT, getColumnName(field, column), getColumnType(field, column));
        StringBuilder columnQuery = new StringBuilder(defaultColumn);

        if (column != null && !column.nullable()) {
            columnQuery
                    .append(SPACE)
                    .append(NOT_NULL);
        }

        return columnQuery.toString();
    }

    private String getColumnName(Field field, Column column) {
        if (column == null || column.name().isBlank()) {
            return field.getName();
        }

        return column.name();
    }

    private String getColumnType(Field field, Column column) {
        Class<?> fieldType = field.getType();
        String columnType = dialect.columnType(fieldType);

        if (fieldType.equals(String.class)) {
            return addLength(columnType, column);
        }

        return columnType;
    }

    private static String addLength(String columnType, Column column) {
        if (column == null) {
            return String.format(LENGTH_FORMAT, columnType, DEFAULT_LENGTH);
        }
        return String.format(LENGTH_FORMAT, columnType, column.length());
    }
}
