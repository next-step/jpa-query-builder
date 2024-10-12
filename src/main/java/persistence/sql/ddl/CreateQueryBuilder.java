package persistence.sql.ddl;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

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

        String tableName = getTableName(clazz);
        sb.append(String.format("create table %s (", tableName));
        List<String> fieldNames = Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .map(field -> {
                    String fieldName = getFieldName(field);
                    String fieldType = getFieldType(field);

                    String result = String.format("%s %s", fieldName, fieldType);
                    if (isIdField(field)) {
                        result += " PRIMARY KEY";
                    }
                    if (isGeneratedValueField(field)) {
                        result += " AUTO_INCREMENT";
                    }
                    if (isNotNullConstraint(field)) {
                        result += " NOT NULL";
                    }
                    return result;
                })
                .toList();

        sb.append(String.join(", ", fieldNames))
                .append(")");

        return sb.toString();
    }

    private static String getTableName(Class<?> clazz) {
        Table annotation = clazz.getAnnotation(Table.class);
        if (annotation == null) {
            return clazz.getSimpleName().toLowerCase();
        }
        
        if (!annotation.name().isEmpty()) {
            return annotation.name();
        }

        return clazz.getSimpleName().toLowerCase();
    }

    private static String getFieldName(Field field) {
        Column annotation = field.getAnnotation(Column.class);
        return (annotation != null && !annotation.name().isEmpty()) ? annotation.name() : field.getName();
    }

    private boolean isNotNullConstraint(Field field) {
        Column annotation = field.getAnnotation(Column.class);
        return annotation != null && !annotation.nullable();
    }

    private String getFieldType(Field field) {
        return FIELD_TYPE.get(field.getType());
    }

    private boolean isIdField(Field field) {
        return Arrays.stream(field.getAnnotations())
                .anyMatch(annotation -> annotation.annotationType().equals(Id.class));
    }

    private boolean isGeneratedValueField(Field field) {
        return Arrays.stream(field.getAnnotations())
                .anyMatch(annotation -> annotation.annotationType().equals(GeneratedValue.class));
    }
}
