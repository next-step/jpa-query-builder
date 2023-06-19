package persistence.sql.dml;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import persistence.sql.ddl.exception.NoIdentifierException;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

public class DmlGenerator {

    private final Class<?> entityClass;

    public DmlGenerator(Class<?> entityClass) {
        this.entityClass = entityClass;
    }

    public String generateInsertQuery(Object entity) {
        if (!entity.getClass().equals(entityClass)) {
            throw new IllegalArgumentException();
        }

        return new StringBuilder()
            .append(String.format("INSERT INTO %s", getTableName()))
            .append(columnsClause())
            .append(valueClause(entity))
            .toString();
    }

    private String getTableName() {
        if (entityClass.isAnnotationPresent(Table.class)) {
            return getTableNameFromAnnotation();
        }
        return entityClass.getSimpleName();
    }

    private String getTableNameFromAnnotation() {
        Table annotation = entityClass.getAnnotation(Table.class);
        String tableName = annotation.name();
        if (tableName.isEmpty()) {
            return entityClass.getSimpleName();
        }
        return tableName;
    }

    private String columnsClause() {
        String columns = Arrays.stream(entityClass.getDeclaredFields())
            .filter(field -> !field.isAnnotationPresent(Transient.class) && !field.isAnnotationPresent(Id.class))
            .map(this::getColumnName)
            .collect(Collectors.joining(", "));
        return String.format(" ( %s ) ", columns);
    }

    private String getColumnName(Field field) {
        if (field.isAnnotationPresent(Column.class)) {
            return getColumnNameFromAnnotation(field);
        }
        return field.getName();
    }

    private String getColumnNameFromAnnotation(Field field) {
        String name = field.getAnnotation(Column.class).name();
        if (name.isEmpty()) {
            return field.getName();
        }
        return name;
    }

    private String valueClause(Object entity) {
        String values = Arrays.stream(entity.getClass().getDeclaredFields())
            .filter(field -> !field.isAnnotationPresent(Transient.class) && !field.isAnnotationPresent(Id.class))
            .map(field -> getFieldValue(entity, field))
            .collect(Collectors.joining(", "));
        return String.format("VALUES ( %s )", values);
    }

    private String getFieldValue(Object entity, Field field) {
        try {
            field.setAccessible(true);
            return convertFieldValue(entity, field);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private String convertFieldValue(Object entity, Field field) throws IllegalAccessException {
        Object value = field.get(entity);
        if (field.getType().equals(String.class)) {
            return String.format("'%s'", value);
        }
        return String.valueOf(value);
    }

    public String generateFindAllQuery() {
        return new StringBuilder()
            .append(selectClause())
            .append(fromClause())
            .toString();
    }

    private String selectClause() {
        String columns = Arrays.stream(entityClass.getDeclaredFields())
            .filter(field -> !field.isAnnotationPresent(Transient.class))
            .map(this::getColumnName)
            .collect(Collectors.joining(", "));
        return String.format("SELECT %s ", columns);
    }

    private String fromClause() {
        return String.format("FROM %s", getTableName());
    }

    public String generateFindByIdQuery(Long id) {
        return new StringBuilder(generateFindAllQuery())
            .append(whereClause(id))
            .toString();
    }

    private String whereClause(Long id) {
        Field idField = Arrays.stream(entityClass.getDeclaredFields())
            .filter(field -> field.isAnnotationPresent(Id.class))
            .findFirst()
            .orElseThrow(() -> new NoIdentifierException(entityClass.getSimpleName()));
        return String.format(" WHERE %s = %d", getColumnName(idField), id);
    }

    public String generateDeleteAllQuery() {
        return deleteClause();
    }

    private String deleteClause() {
        return String.format("DELETE FROM %s", getTableName());
    }

    public String generateDeleteByIdQuery(Long id) {
        return new StringBuilder(generateDeleteAllQuery())
            .append(whereClause(id))
            .toString();
    }
}
