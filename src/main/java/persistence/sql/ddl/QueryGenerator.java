package persistence.sql.ddl;

import jakarta.persistence.Id;
import java.lang.reflect.Field;

public class QueryGenerator {
    public String create(final Class<?> clazz) {
        final StringBuilder sql = new StringBuilder("CREATE TABLE %s (\n".formatted(clazz.getSimpleName().toUpperCase()));

        final Field[] fields = Person.class.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            final Field field = fields[i];
            final String columnName = field.getName().toLowerCase();
            final String columnType = getColumnType(field);

            sql.append("    ").append(columnName).append(" ").append(columnType);

            if (field.isAnnotationPresent(Id.class)) {
                sql.append(" PRIMARY KEY");
            }

            if (i < fields.length - 1) {
                sql.append(",");
            }

            sql.append("\n");
        }

        sql.append(");");
        return sql.toString();
    }

    private String getColumnType(final Field field) {
        if (field.getType() == Long.class) {
            return "BIGINT";
        } else if (field.getType() == String.class) {
            return "VARCHAR(255)";
        } else if (field.getType() == Integer.class) {
            return "INTEGER";
        } else {
            return "VARCHAR(255)";
        }
    }
}
