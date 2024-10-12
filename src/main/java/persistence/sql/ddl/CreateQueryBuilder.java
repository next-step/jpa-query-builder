package persistence.sql.ddl;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class CreateQueryBuilder {
    private static final Map<Class<?>, String> FIELD_TYPE = Map.of(
            Long.class, "BIGINT",
            String.class, "VARCHAR",
            Integer.class, "INT"
    );

    public String createTableQuery(Class<?> clazz) {
        String tableName = getTableName(clazz);
        String fieldDefinitions = getFieldDefinitions(clazz);
        return String.format("create table %s (%s)", tableName, fieldDefinitions);
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

    private String getFieldDefinitions(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .map(this::getFieldDefinition)
                .collect(Collectors.joining(", "));
    }

    private String getFieldDefinition(Field field) {
        String fieldName = getFieldName(field);
        String fieldType = getFieldType(field);
        StringBuilder result = new StringBuilder(String.format("%s %s", fieldName, fieldType));
        if (isIdField(field)) {
            result.append(" PRIMARY KEY");
        }
        if (isGeneratedValueField(field)) {
            result.append(" AUTO_INCREMENT");
        }
        if (isNotNullConstraint(field)) {
            result.append(" NOT NULL");
        }
        return result.toString();
    }

    private static String getFieldName(Field field) {
        Column annotation = field.getAnnotation(Column.class);
        if (annotation == null) {
            return field.getName();
        }
        if (!annotation.name().isEmpty()) {
            return annotation.name();
        }

        return field.getName();
    }

    private boolean isNotNullConstraint(Field field) {
        if (!field.isAnnotationPresent(Column.class)) {
            return false;
        }
        Column annotation = field.getAnnotation(Column.class);
        return !annotation.nullable();
    }

    private String getFieldType(Field field) {
        return FIELD_TYPE.get(field.getType());
    }

    private boolean isIdField(Field field) {
        return field.isAnnotationPresent(Id.class);
    }

    private boolean isGeneratedValueField(Field field) {
        return field.isAnnotationPresent(GeneratedValue.class);
    }
}
