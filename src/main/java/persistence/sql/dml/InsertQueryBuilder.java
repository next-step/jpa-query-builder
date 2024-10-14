package persistence.sql.dml;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import persistence.sql.MetadataUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

public class InsertQueryBuilder {
    public String getInsertQuery(Object object) {
        Class<?> clazz = object.getClass();
        MetadataUtils metadataUtils = new MetadataUtils(clazz);
        String tableName = metadataUtils.getTableName();
        String tableColumns = columnsClause(clazz);
        String tableValues = valueClause(object);
        return String.format("insert into %s (%s) VALUES (%s)", tableName, tableColumns, tableValues);
    }

    private String columnsClause(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(GeneratedValue.class))
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .map(this::getTableColumn)
                .collect(Collectors.joining(", "));
    }

    private String getTableColumn(Field field) {
        Column annotation = field.getAnnotation(Column.class);
        if (annotation == null) {
            return field.getName();
        }
        if (!annotation.name().isEmpty()) {
            return annotation.name();
        }

        return field.getName();
    }

    private String valueClause(Object object) {
        return Arrays.stream(object.getClass().getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(GeneratedValue.class))
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .map(field -> getTableValue(object, field))
                .collect(Collectors.joining(", "));
    }

    private String getTableValue(Object object, Field field) {
        field.setAccessible(true);
        try {
            Object fieldValue = field.get(object);
            if (fieldValue == null) {
                return "NULL";
            }
            if (fieldValue instanceof String) {
                return String.format("'%s'", fieldValue);
            }
            return fieldValue.toString();

        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
