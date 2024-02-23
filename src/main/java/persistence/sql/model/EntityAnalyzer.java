package persistence.sql.model;

import jakarta.persistence.Table;
import jakarta.persistence.*;
import util.CaseConverter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EntityAnalyzer {

    private final Class<?> entity;

    public EntityAnalyzer(Class<?> entity) {
        validateEntity(entity);

        this.entity = entity;
    }

    private void validateEntity(Class<?> entity) {
        if (!entity.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException("Class is not an entity: " + entity.getSimpleName());
        }
    }

    public String analyzeTableName() {
        String tableName = getTableName();
        return CaseConverter.pascalToSnake(tableName);
    }

    private String getTableName() {
        Table table = entity.getDeclaredAnnotation(Table.class);

        if (table != null && hasTableName(table)) {
            return table.name();
        }

        String entityName = entity.getSimpleName();
        return CaseConverter.camelToSnake(entityName);
    }

    private boolean hasTableName(Table table) {
        String name = table.name();
        return !name.isEmpty();
    }

    public List<Column> analyzeColumns() {
        Field[] declaredFields = entity.getDeclaredFields();
        return Arrays.stream(declaredFields)
                .filter(declaredField -> !hasTransientAnnotation(declaredField))
                .map(this::analyzeColumn)
                .collect(Collectors.toList());
    }

    private boolean hasTransientAnnotation(Field field) {
        return field.isAnnotationPresent(Transient.class);
    }

    private Column analyzeColumn(Field field) {
        SqlType columnType = getColumnType(field);
        String columnName = getColumnName(field);
        List<SqlConstraint> columnConstraints = getColumnConstraints(field);
        return new Column(columnType, columnName, columnConstraints);
    }

    private SqlType getColumnType(Field field) {
        Class<?> fieldType = field.getType();
        return SqlType.of(fieldType);
    }

    private String getColumnName(Field field) {
        jakarta.persistence.Column column = field.getDeclaredAnnotation(jakarta.persistence.Column.class);

        if (column != null && hasColumnName(column)) {
            return column.name();
        }

        String fieldName = field.getName();
        return CaseConverter.camelToSnake(fieldName);
    }

    private boolean hasColumnName(jakarta.persistence.Column column) {
        String name = column.name();
        return !name.isEmpty();
    }

    private List<SqlConstraint> getColumnConstraints(Field field) {
        List<SqlConstraint> columnAnnotationConstraints = getColumnAnnotationConstraints(field);
        List<SqlConstraint> generationValueAnnotationConstraints = getGenerationValueAnnotationConstraints(field);
        List<SqlConstraint> idAnnotationConstraints = getIdAnnotationConstraints(field);

        List<SqlConstraint> columnConstraints = new ArrayList<>();
        columnConstraints.addAll(columnAnnotationConstraints);
        columnConstraints.addAll(generationValueAnnotationConstraints);
        columnConstraints.addAll(idAnnotationConstraints);
        return columnConstraints;
    }

    private List<SqlConstraint> getColumnAnnotationConstraints(Field field) {
        jakarta.persistence.Column column = field.getDeclaredAnnotation(jakarta.persistence.Column.class);
        if (column == null) {
            return List.of();
        }

        List<SqlConstraint> constraints = new ArrayList<>();

        boolean nullable = column.nullable();
        if (!nullable) {
            constraints.add(SqlConstraint.NOT_NULL);
        }

        return constraints;
    }

    private List<SqlConstraint> getGenerationValueAnnotationConstraints(Field field) {
        GeneratedValue generatedValue = field.getDeclaredAnnotation(GeneratedValue.class);

        if (generatedValue == null) {
            return List.of();
        }

        List<SqlConstraint> constraints = new ArrayList<>();

        GenerationType strategy = generatedValue.strategy();
        SqlConstraint constraint = SqlConstraint.of(strategy);
        constraints.add(constraint);

        return constraints;
    }

    private List<SqlConstraint> getIdAnnotationConstraints(Field field) {
        Id id = field.getDeclaredAnnotation(Id.class);

        if (id == null) {
            return List.of();
        }

        List<SqlConstraint> constraints = new ArrayList<>();

        Class<? extends Annotation> annotationType = id.annotationType();
        SqlConstraint constraint = SqlConstraint.of(annotationType);
        constraints.add(constraint);

        return constraints;
    }
}
