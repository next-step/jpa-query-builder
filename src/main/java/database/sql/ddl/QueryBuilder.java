package database.sql.ddl;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class QueryBuilder {
    public String buildCreateQuery(Class<?> entityClass) {
        String tableName = extractTableName(entityClass);
        List<String> fields = extractFields(entityClass);
        return String.format("CREATE TABLE %s (%s)", tableName, String.join(", ", fields));
    }

    public String buildDeleteQuery(Class<?> entityClass) {
        String tableName = extractTableName(entityClass);
        return String.format("DROP TABLE %s", tableName);
    }

    static String extractTableName(Class<?> entityClass) {
        Table tableAnnotation = entityClass.getAnnotation(Table.class);
        if (tableAnnotation != null && !tableAnnotation.name().isEmpty()) {
            return tableAnnotation.name();
        } else {
            return entityClass.getSimpleName();
        }
    }

    static List<String> extractFields(Class<?> entityClass) {
        Field[] fields = entityClass.getDeclaredFields();
        return Arrays.stream(fields)
                .filter(QueryBuilder::notTransientField)
                .map(QueryBuilder::convertFieldToDdl)
                .collect(Collectors.toList());
    }

    static boolean notTransientField(Field field) {
        return field.getAnnotation(Transient.class) == null;
    }

    static String convertFieldToDdl(Field field) {
        boolean isId = field.isAnnotationPresent(Id.class);

        List<String> list = new ArrayList<>();
        list.add(extractName(field));
        list.add(convertType(field.getType()));
        if (isId) {
            list.add("AUTO_INCREMENT PRIMARY KEY");
        }
        if (!isId) list.add(extractNullability(field));

        return String.join(" ", list);
    }

    static String extractName(Field field) {
        Column columnAnnotation = field.getAnnotation(Column.class);
        if (columnAnnotation != null && !columnAnnotation.name().isEmpty()) {
            return columnAnnotation.name();
        } else {
            return field.getName();
        }
    }

    static String convertType(Class<?> type) {
        switch (type.getName()) {
            case "java.lang.Long":
                return "BIGINT";
            case "java.lang.String":
                return "VARCHAR(100)";
            case "java.lang.Integer":
                return "INT";
            default:
                throw new RuntimeException("Cannot convert type: " + type.getName());
        }
    }

    static String extractNullability(Field field) {
        Column columnAnnotation = field.getAnnotation(Column.class);
        if (columnAnnotation != null && !columnAnnotation.nullable()) {
            return "NOT NULL";
        } else {
            return "NULL";
        }
    }
}
