package persistence.sql.ddl;

import jakarta.persistence.Column;

import java.lang.reflect.Field;

public class ColumnMapper {
    private static final String SPACE = " ";
    private final Dialect dialect;

    public ColumnMapper(Dialect dialect) {
        this.dialect = dialect;
    }

    String column(Field field) {
        Column column = field.getAnnotation(Column.class);
        String defaultColumn = String.format("%s %s", getColumnName(field, column), getColumnType(field, column));
        StringBuilder columnQuery = new StringBuilder(defaultColumn);

        if (!column.nullable()) {
            columnQuery
                    .append(SPACE)
                    .append("not null");
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
            return String.format("%s(%d)", columnType, 255);
        }
        return String.format("%s(%d)", columnType, column.length());
    }
}
