package persistence.sql.ddl;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class QueryBuilder {
    public String create(Class<?> entity) {
        String createTableQuery = this.getCreateTableQuery(entity);
        String columnDefinitions = this.generateColumnDefinitions(entity);

        return "%s (%s);".formatted(createTableQuery, columnDefinitions);
    }

    private String getCreateTableQuery(Class<?> entity) {
        String entityName = entity.getSimpleName();

        return "CREATE TABLE %s".formatted(entityName);
    }

    private String generateColumnDefinitions(Class<?> entity) {
        Field[] fields = entity.getDeclaredFields();
        List<String> columns = Arrays.stream(fields).map(field -> "%s %s".formatted(field.getName(), this.mapFieldTypeToSQLType(field))).toList();
        return String.join(", ", columns);
    }

    private String mapFieldTypeToSQLType(Field field) {
        if (field.getType().equals(String.class)) {
            return "VARCHAR(255)";
        } else if (field.getType().equals(Integer.class)) {
            return "INTEGER";
        } else if (field.getType().equals(Long.class)) {
            return "BIGINT";
        }
        return "";
    }
}
