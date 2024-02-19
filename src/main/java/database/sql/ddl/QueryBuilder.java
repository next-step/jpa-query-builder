package database.sql.ddl;

import jakarta.persistence.*;

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

    private String extractTableName(Class<?> entityClass) {
        Table tableAnnotation = entityClass.getAnnotation(Table.class);
        if (tableAnnotation != null && !tableAnnotation.name().isEmpty()) {
            return tableAnnotation.name();
        } else {
            return entityClass.getSimpleName();
        }
    }

    private List<String> extractFields(Class<?> entityClass) {
        Field[] fields = entityClass.getDeclaredFields();
        return Arrays.stream(fields)
                .filter(this::notTransientField)
                .map(this::convertFieldToDdl)
                .collect(Collectors.toList());
    }

    private boolean notTransientField(Field field) {
        return field.getAnnotation(Transient.class) == null;
    }

    private String convertFieldToDdl(Field field) {
        boolean isId = field.isAnnotationPresent(Id.class);

        List<String> list = new ArrayList<>();

        list.add(extractName(field));

        list.add(convertType(field.getType()));

        if (isId) {
            GeneratedValue generatedValueAnnotation = field.getAnnotation(GeneratedValue.class);
            if (generatedValueAnnotation != null && generatedValueAnnotation.strategy() == GenerationType.IDENTITY) {
                list.add("AUTO_INCREMENT");
            }
            list.add("PRIMARY KEY");
        }

        if (!isId) list.add(extractNullability(field));

        return String.join(" ", list);
    }

    private String extractName(Field field) {
        Column columnAnnotation = field.getAnnotation(Column.class);
        if (columnAnnotation != null && !columnAnnotation.name().isEmpty()) {
            return columnAnnotation.name();
        } else {
            return field.getName();
        }
    }

    private String convertType(Class<?> type) {
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

    private String extractNullability(Field field) {
        Column columnAnnotation = field.getAnnotation(Column.class);
        if (columnAnnotation != null && !columnAnnotation.nullable()) {
            return "NOT NULL";
        } else {
            return "NULL";
        }
    }
}
