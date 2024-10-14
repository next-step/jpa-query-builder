package persistence.sql.ddl;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.lang.reflect.Field;

public class QueryGenerator {
    private static final String INDENTATION = "    ";
    private static final int VARCHAR_DEFAULT_LENGTH = 255;

    public String create(final Class<?> clazz) {
        final StringBuilder sql = new StringBuilder();
        final String tableName = getTableName(clazz);
        appendTableHeader(sql, tableName);
        appendColumnDefinitions(sql, clazz);
        appendTableFooter(sql);
        return sql.toString();
    }

    private String getTableName(final Class<?> clazz) {
        final Table tableAnnotation = clazz.getAnnotation(Table.class);
        if (tableAnnotation != null && !tableAnnotation.name().isEmpty()) {
            return tableAnnotation.name().toUpperCase();
        }
        return clazz.getSimpleName().toUpperCase();
    }
    private void appendTableHeader(final StringBuilder sql, final String tableName) {
        sql.append("CREATE TABLE ").append(tableName).append(" (\n");
    }

    private void appendColumnDefinitions(final StringBuilder sql, final Class<?> entityClass) {
        final Field[] fields = entityClass.getDeclaredFields();
        boolean firstColumn = true;
        for (final Field field : fields) {
            if (field.isAnnotationPresent(Transient.class)) {
                continue;
            }
            if (!firstColumn) {
                sql.append(",\n");
            }
            appendColumnDefinition(sql, field);
            firstColumn = false;
        }    }

    private void appendColumnDefinition(final StringBuilder sql, final Field field) {
        final String columnName = getColumnName(field);
        final String columnType = getColumnType(field);

        sql.append(INDENTATION)
                .append(columnName)
                .append(" ")
                .append(columnType);

        if (isIdentity(field)) {
            sql.append(" AUTO_INCREMENT");
        }
        if (!isNullable(field)) {
            sql.append(" NOT NULL");
        }
        if (isIdField(field)) {
            sql.append(" PRIMARY KEY");
        }
    }

    private String getColumnName(final Field field) {
        final Column column = field.getAnnotation(Column.class);
        return (column != null && !column.name().isEmpty()) ? column.name() : field.getName().toLowerCase();
    }
    private boolean isIdentity(final Field field) {
        final GeneratedValue generatedValue = field.getAnnotation(GeneratedValue.class);
        return generatedValue != null && generatedValue.strategy() == GenerationType.IDENTITY;
    }

    private boolean isNullable(final Field field) {
        final Column column = field.getAnnotation(Column.class);
        return column == null || column.nullable();
    }

    private boolean isIdField(final Field field) {
        return field.isAnnotationPresent(Id.class);
    }

    private String getColumnType(final Field field) {
        final Class<?> type = field.getType();
        if (type == Long.class) {
            return "BIGINT";
        } else if (type == String.class) {
            return "VARCHAR(" + VARCHAR_DEFAULT_LENGTH + ")";
        } else if (type == Integer.class) {
            return "INTEGER";
        } else {
            return "VARCHAR(" + VARCHAR_DEFAULT_LENGTH + ")";
        }
    }

    private void appendTableFooter(final StringBuilder sql) {
        sql.append(");");
    }
}
