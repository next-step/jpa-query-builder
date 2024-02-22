package util;

import jakarta.persistence.*;
import persistence.sql.model.Column;
import persistence.sql.model.SqlConstraint;
import persistence.sql.model.SqlType;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EntityAnalyzer {

    public static String getTableName(Class<?> clazz) {
        validateEntity(clazz);

        String tableName = clazz.getSimpleName();
        return CaseConverter.pascalToSnake(tableName);
    }

    public static List<Column> getColumns(Class<?> clazz) {
        validateEntity(clazz);

        Field[] declaredFields = clazz.getDeclaredFields();
        return Arrays.stream(declaredFields)
                .filter(declaredField -> !hasTransientAnnotation(declaredField))
                .map(declaredField -> {
                    SqlType columnType = getColumnType(declaredField);
                    String columnName = getColumnName(declaredField);
                    List<SqlConstraint> columnConstraints = getColumnConstraints(declaredField);
                    return new Column(columnType, columnName, columnConstraints);
                })
                .collect(Collectors.toList());
    }

    private static boolean hasTransientAnnotation(Field field) {
        return field.isAnnotationPresent(Transient.class);
    }

    private static void validateEntity(Class<?> clazz) {
        if (!clazz.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException("Class is not an entity: " + clazz.getSimpleName());
        }
    }

    private static SqlType getColumnType(Field field) {
        Class<?> fieldType = field.getType();
        return SqlType.of(fieldType);
    }

    private static String getColumnName(Field field) {
        jakarta.persistence.Column column = field.getDeclaredAnnotation(jakarta.persistence.Column.class);

        if (column != null && hasColumnName(column)) {
            return column.name();
        }

        String fieldName = field.getName();
        return CaseConverter.camelToSnake(fieldName);
    }

    private static boolean hasColumnName(jakarta.persistence.Column column) {
        String name = column.name();
        return !name.isEmpty();
    }

    private static List<SqlConstraint> getColumnConstraints(Field field) {
        List<SqlConstraint> columnConstraints = new ArrayList<>();

        extractColumnConstraint(field, columnConstraints);
        extractGenerationValueConstraint(field, columnConstraints);
        extractIdConstraint(field, columnConstraints);

        return columnConstraints;
    }

    private static void extractColumnConstraint(Field field, List<SqlConstraint> columnConstraints) {
        jakarta.persistence.Column column = field.getDeclaredAnnotation(jakarta.persistence.Column.class);

        if (column == null) {
            return;
        }

        boolean nullable = column.nullable();
        if (!nullable) {
            columnConstraints.add(SqlConstraint.NOT_NULL);
        }
    }

    private static void extractGenerationValueConstraint(Field field, List<SqlConstraint> columnConstraints) {
        GeneratedValue generatedValue = field.getDeclaredAnnotation(GeneratedValue.class);

        if (generatedValue == null) {
            return;
        }

        GenerationType strategy = generatedValue.strategy();
        SqlConstraint constraint = SqlConstraint.of(strategy);
        columnConstraints.add(constraint);
    }

    private static void extractIdConstraint(Field field, List<SqlConstraint> columnConstraints) {
        Id id = field.getDeclaredAnnotation(Id.class);

        if (id == null) {
            return;
        }

        Class<? extends Annotation> annotationType = id.annotationType();
        SqlConstraint constraint = SqlConstraint.of(annotationType);
        columnConstraints.add(constraint);
    }
}
