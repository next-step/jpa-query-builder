package database.sql.ddl;

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
        list.add(field.getName());
        list.add(convertType(field.getType()));
        if (isId) {
            list.add("unsigned");
        }
        if (isId) {
            list.add("auto_increment");
        }
        return String.join(" ", list);
    }

    private static String convertType(Class<?> type) {
        if (type.getName().equals("java.lang.Long")) {
            return "bigint";
        } else if (type.getName().equals("java.lang.String")) {
            return "varchar(100)";
        } else if (type.getName().equals("java.lang.Integer")) {
            return "int";
        } else {
            throw new RuntimeException("Cannot convert type: " + type.getName());
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
