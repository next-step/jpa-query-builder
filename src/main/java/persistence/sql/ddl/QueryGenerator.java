package persistence.sql.ddl;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.lang.reflect.Field;

public class QueryGenerator {
    private static final String INDENTATION = "    ";
    private static final int VARCHAR_DEFAULT_LENGTH = 255;

    public String create(final Class<?> clazz) {
        final StringBuilder sql = new StringBuilder();
        appendTableHeader(sql, clazz.getSimpleName().toUpperCase());
        appendColumnDefinitions(sql, clazz);
        appendTableFooter(sql);
        return sql.toString();
    }

    private void appendTableHeader(final StringBuilder sql, final String tableName) {
        sql.append("CREATE TABLE ").append(tableName).append(" (\n");
    }

    private void appendColumnDefinitions(final StringBuilder sql, final Class<?> entityClass) {
        final Field[] fields = entityClass.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            appendColumnDefinition(sql, fields[i]);
            if (i < fields.length - 1) {
                sql.append(",");
            }
            sql.append("\n");
        }
    }

    private void appendColumnDefinition(final StringBuilder sql, final Field field) {
        final String columnName = field.getName().toLowerCase();
        final String columnType = getColumnType(field);

        sql.append(INDENTATION)
                .append(columnName)
                .append(" ")
                .append(columnType);

        if (isIdentity(field)) {
            sql.append(" AUTO_INCREMENT");
        }

        if (isIdField(field)) {
            sql.append(" PRIMARY KEY");
        }
    }

    private boolean isIdentity(final Field field) {
        final GeneratedValue generatedValue = field.getAnnotation(GeneratedValue.class);
        return generatedValue != null && generatedValue.strategy() == GenerationType.IDENTITY;
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
