package persistence.sql.dml;

import jakarta.persistence.*;
import persistence.sql.TableColumn;
import persistence.sql.TableMeta;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

public abstract class DMLQueryBuilder {
    private final Class<?> clazz;
    TableMeta tableMeta;

    protected DMLQueryBuilder(Class<?> clazz) {
        this.clazz = clazz;
        this.tableMeta = new TableMeta(clazz);
    }

    String setClause(Object entity) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(this::isPersistentField)
                .filter(field -> getFieldValue(entity, field) != null)
                .map(field -> getColumnName(field) + " = " + getFieldValue(entity, field))
                .collect(Collectors.joining(", "));
    }

    String idClause (Object entity) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Id.class))
                .map(field -> getColumnName(field) + " = " + getFieldValue(entity, field))
                .collect(Collectors.joining(" AND "));
    }

    String getTableName() {
        return tableMeta.tableName();
    }

    String columnsClause() {
        return tableMeta.tableColumn().stream()
                .map(TableColumn::name).reduce((s1, s2) -> s1 + ", " + s2).orElseThrow();
    }

    String valueClause(Object entity) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(this::isPersistentField)
                .map(field -> getFieldValue(entity, field))
                .collect(Collectors.joining(", "));
    }

    private String getFieldValue(Object entity, Field field) {
        try {
            field.setAccessible(true);
            Object value = field.get(entity);
            return formatValue(value);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private String formatValue(Object value) {
        if (value instanceof String) {
            return "'" + value + "'";
        }
        return value != null ? value.toString() : null;
    }

    private String getColumnName(Field field) {
        if (field.isAnnotationPresent(Column.class)) {
            Column column = field.getAnnotation(Column.class);
            if (!column.name().isEmpty()) {
                return column.name();
            }
        }
        return field.getName();
    }

    private boolean isPersistentField(Field field) {
        return !(field.isAnnotationPresent(Id.class) && field.isAnnotationPresent(GeneratedValue.class))
                && !field.isAnnotationPresent(Transient.class);
    }
}