package persistence.sql.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Transient;
import persistence.exception.NotEntityException;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Table {
    private final String name;
    private final List<Column> columns;

    public Table(String name, List<Column> columns) {
        this.name = name;
        this.columns = columns;
    }

    public static Table of(Class<?> target) {
        checkIsEntity(target);
        String tableName = getTableName(target);
        List<Column> columns = getColumns(target);
        return new Table(tableName, columns);
    }

    private static void checkIsEntity(Class<?> target) {
        if (!target.isAnnotationPresent(Entity.class)) {
            throw new NotEntityException();
        }
    }

    private static String getTableName(Class<?> target) {
        return Optional.ofNullable(target.getAnnotation(jakarta.persistence.Table.class))
                .map(jakarta.persistence.Table::name)
                .filter(name -> !name.isBlank())
                .orElse(target.getSimpleName());
    }

    private static List<Column> getColumns(Class<?> target) {
        return getTargetFields(target).stream()
                .map(Column::from)
                .collect(Collectors.toList());
    }

    private static List<Field> getTargetFields(Class<?> target) {
        return Arrays.stream(target.getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .collect(Collectors.toList());
    }

    public String getName() {
        return name;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public IdColumn getIdColumn() {
        return (IdColumn) columns.stream()
                .filter(Column::isId)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Id not found"));
    }
}
