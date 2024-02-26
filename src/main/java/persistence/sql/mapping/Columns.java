package persistence.sql.mapping;

import jakarta.persistence.Entity;
import jakarta.persistence.Transient;
import persistence.sql.ddl.exception.AnnotationMissingException;
import persistence.sql.ddl.exception.IdAnnotationMissingException;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class Columns implements Iterable<ColumnData> {

    private final List<ColumnData> columns;

    private Columns(List<ColumnData> columns) {
        this.columns = columns;
    }

    @Override
    public Iterator<ColumnData> iterator() {
        return columns.iterator();
    }

    public static Columns createColumns(Class<?> clazz) {
        checkIsEntity(clazz);
        List<ColumnData> columns = Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .map(ColumnData::createColumn)
                .collect(Collectors.toList());

        checkHasPrimaryKey(columns);
        return new Columns(columns);
    }

    public static Columns createColumnsWithValue(Class<?> clazz, Object entity) {
        List<ColumnData> columns = Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .map(field -> ColumnData.createColumnWithValue(field, entity))
                .collect(Collectors.toList());

        checkHasPrimaryKey(columns);
        return new Columns(columns);
    }

    public List<String> getNames() {
        return columns.stream()
                .filter(ColumnData::isNotPrimaryKey)
                .map(ColumnData::getName)
                .collect(Collectors.toList());
    }

    public List<Object> getValues() {
        return columns.stream()
                .filter(ColumnData::isNotPrimaryKey)
                .map(ColumnData::getValue)
                .collect(Collectors.toList());
    }

    public List<ColumnData> getKeyColumns() {
        return columns.stream()
                .filter(ColumnData::hasKeyType)
                .collect(Collectors.toList());
    }

    private static void checkIsEntity(Class<?> entityClazz) {
        if (!entityClazz.isAnnotationPresent(Entity.class)) {
            throw new AnnotationMissingException("Entity 어노테이션이 없습니다.");
        }
    }

    private static void checkHasPrimaryKey(List<ColumnData> columns) {
        if (columns.stream().noneMatch(ColumnData::isPrimaryKey)) {
            throw new IdAnnotationMissingException();
        }
    }
}
