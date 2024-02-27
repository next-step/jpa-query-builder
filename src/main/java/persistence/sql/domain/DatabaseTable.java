package persistence.sql.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DatabaseTable {

    private final String name;

    private final List<DatabaseColumn> columns;

    private final DatabasePrimaryColumn primaryColumn;

    public DatabaseTable(Class<?> clazz) {
        this.name = getTableName(clazz);
        this.columns = buildColumns(clazz, null);
        this.primaryColumn = buildPrimaryColumn(clazz, null);
    }

    public <T> DatabaseTable(T entity) {
        Class<?> clazz = entity.getClass();
        this.name = getTableName(clazz);
        this.columns = buildColumns(clazz, entity);
        this.primaryColumn = buildPrimaryColumn(clazz, entity);
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

    private List<DatabaseColumn> buildColumns(Class<?> clazz, Object object) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(this::isMappingColumn)
                .filter(column -> !isPrimaryColumn(column))
                .map(column -> DatabaseColumn.fromField(column, object))
                .collect(Collectors.toList());
    }

    private DatabasePrimaryColumn buildPrimaryColumn(Class<?> clazz, Object object) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(this::isMappingColumn)
                .filter(this::isPrimaryColumn)
                .findFirst()
                .map(column -> DatabasePrimaryColumn.fromField(column, object))
                .orElseThrow(() -> new IllegalStateException("entity primary key not exist"));
    }

    private boolean isPrimaryColumn(Field field) {
        return field.isAnnotationPresent(Id.class);
    }

    private boolean isMappingColumn(Field field) {
        return !field.isAnnotationPresent(Transient.class);
    }

    public String getName() {
        return name;
    }

    public DatabasePrimaryColumn getPrimaryColumn() {
        return DatabasePrimaryColumn.copy(primaryColumn);
    }

    public List<ColumnOperation> getAllColumns() {
        return Stream.concat(Stream.of(getPrimaryColumn()), columns.stream().map(DatabaseColumn::copy))
                .collect(Collectors.toList());
    }
}
