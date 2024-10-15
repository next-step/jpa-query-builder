package persistence.sql.ddl;

import jakarta.persistence.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
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
        List<String> columns = Arrays.stream(fields).filter(field -> !field.isAnnotationPresent(Transient.class)).map(field -> "%s %s".formatted(this.getColumnNameFromAnnotation(field).isEmpty() ? field.getName() : this.getColumnNameFromAnnotation(field), this.mapFieldTypeToSQLType(field) + this.mapFieldAnnotationToSQLType((field)))).toList();
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

    private String mapFieldAnnotationToSQLType(Field field) {
        List<String> query = new ArrayList<>();

        if (field.isAnnotationPresent(Id.class)) {
            query.add("PRIMARY KEY");
        }
        if (field.isAnnotationPresent(Column.class)) {
            Column column = field.getAnnotation(Column.class);
            if (!column.nullable()) {
                query.add("NOT NULL");
            }
        }
        if (field.isAnnotationPresent(GeneratedValue.class)) {
            GeneratedValue generatedValue = field.getAnnotation(GeneratedValue.class);
            if (generatedValue.strategy().equals(GenerationType.IDENTITY)) {
                query.add("AUTO_INCREMENT");
            }
        }

        String result = String.join(" ", query);
        return result.isEmpty() ? "" : " " + result;
    }

    private String getColumnNameFromAnnotation(Field field) {
        if (!field.isAnnotationPresent(Column.class)) {
            return "";
        }
        Column column = field.getAnnotation(Column.class);
        return column.name();
    }
}
