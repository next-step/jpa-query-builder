package persistence.sql.ddl;

import jakarta.persistence.Id;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class CreateQueryBuilder {
    private static final Map<Class<?>, String> FIELD_TYPE = Map.of(
            Long.class, "BIGINT",
            String.class, "VARCHAR",
            Integer.class, "INT"
    );

    public String createTableQuery(Class<?> clazz) {
        StringBuilder sb = new StringBuilder();

        String tableName = clazz.getSimpleName().toLowerCase();
        sb.append(String.format("create table %s (", tableName));
        List<String> fieldNames = Arrays.stream(clazz.getDeclaredFields())
                .map(field -> {
                    String fieldName = field.getName();
                    String fieldType = getFieldType(field);

                    if (isIdField(field)) {
                        return String.format("%s %s PRIMARY KEY", fieldName, fieldType);
                    }
                    return String.format("%s %s", fieldName, fieldType);
                })
                .toList();

        sb.append(String.join(", ", fieldNames))
                .append(")");

        return sb.toString();
    }

    private String getFieldType(Field field) {
        return FIELD_TYPE.get(field.getType());
    }

    private boolean isIdField(Field field) {
        return Arrays.stream(field.getAnnotations())
                .anyMatch(annotation -> annotation.annotationType().equals(Id.class));
    }
}
