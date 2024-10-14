package persistence.sql.ddl;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import persistence.sql.MetadataUtils;
import persistence.sql.domain.FieldType;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

public class CreateQueryBuilder {

    public String createTableQuery(Class<?> clazz) {
        MetadataUtils metadataUtils = new MetadataUtils(clazz);
        String tableName = metadataUtils.getTableName();
        String fieldDefinitions = getFieldDefinitions(clazz);
        return String.format("create table %s (%s)", tableName, fieldDefinitions);
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
        return FieldType.getSqlTypeByClass(field.getType());
    }

    private boolean isIdField(Field field) {
        return field.isAnnotationPresent(Id.class);
    }

    private boolean isGeneratedValueField(Field field) {
        return field.isAnnotationPresent(GeneratedValue.class);
    }
}
