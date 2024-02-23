package persistence.sql.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DatabaseTable {

    private final String name;

    private final DatabaseColumns columns;

    public DatabaseTable(Class<?> clazz) {
        this.name = getTableName(clazz);
        this.columns = buildColumns(clazz, null);
    }

    public <T> DatabaseTable(T entity) {
        Class<?> clazz = entity.getClass();
        this.name = getTableName(clazz);
        this.columns = buildColumns(clazz, entity);
    }

    private String getTableName(Class<?> clazz) {
        if (!clazz.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException("entity annotation is required");
        }
        Table table = clazz.getAnnotation(Table.class);
        if (table != null && table.name().length() > 0) {
            return table.name();
        }
        return clazz.getSimpleName();
    }

    private DatabaseColumns buildColumns(Class<?> clazz, Object object) {
        List<DatabaseColumn> columns = Arrays.stream(clazz.getDeclaredFields())
                .filter(this::isMappingColumn)
                .map(column -> buildColumn(column, object))
                .collect(Collectors.toList());
        return new DatabaseColumns(columns);
    }

    private boolean isMappingColumn(Field field) {
        return !field.isAnnotationPresent(Transient.class);
    }

    private DatabaseColumn buildColumn(Field field, Object object) {
        return DatabaseColumn.fromField(field, object);
    }

    public String getName() {
        return name;
    }

    public List<DatabaseColumn> getColumns() {
        return columns.getColumns();
    }

    public String columnClause() {
        return columns.columnClause();
    }

    public String valueClause() {
        return columns.valueClause();
    }

    public String whereClause() {
        return columns.whereClause();
    }

    public String getIdColumnName() {
        return columns.getIdColumnName();
    }
}
