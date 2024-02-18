package database.sql.ddl;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class QueryBuilder {

    private static List<String> extractFields(Class<?> entityClass) {
        Field[] fields = entityClass.getDeclaredFields();
        return Arrays.stream(fields)
                .map(QueryBuilder::convertFieldToDdl).collect(Collectors.toList());
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

    private static String convertType(Class<?> type) {
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

    static String extractName(Field field) {
        Column columnAnnotation = field.getAnnotation(Column.class);
        if (columnAnnotation != null) {
            String name = columnAnnotation.name();
            if (!name.isEmpty())
                return name;
        }
        return field.getName();
    }

    private static String extractNullability(Field field) {
        Column columnAnnotation = field.getAnnotation(Column.class);
        if (columnAnnotation != null && !columnAnnotation.nullable()) {
            return "NOT NULL";
        } else {
            return "NULL";
        }
    }

    private static String extractTableName(Class<?> entityClass) {
        return entityClass.getSimpleName();
//        return entityClass.getName();
//        Entity entityAnnotation = entityClass.getAnnotation(Entity.class);
    }

    public String buildCreateQuery(Class<?> entityClass) {
        String tableName = extractTableName(entityClass);
        List<String> fields = extractFields(entityClass);
        return String.format("CREATE TABLE %s (%s)", tableName, String.join(", ", fields));
    }
}
