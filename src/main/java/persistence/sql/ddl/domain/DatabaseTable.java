package persistence.sql.ddl.domain;

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

    private final List<DatabaseColumn> columns;

    public DatabaseTable(Class<?> clazz) {
        if (!clazz.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException("entity annotation is required");
        }
        this.columns = buildColumns(clazz);
        Table table = clazz.getAnnotation(Table.class);
        if (table != null && table.name().length() > 0) {
            this.name = table.name();
            return;
        }
        this.name = clazz.getSimpleName();
    }

    private List<DatabaseColumn> buildColumns(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(this::isMappingColumn)
                .map(this::buildColumn)
                .collect(Collectors.toList());
    }

    private boolean isMappingColumn(Field field) {
        return !field.isAnnotationPresent(Transient.class);
    }

    private DatabaseColumn buildColumn(Field field) {
        ColumnName name = new ColumnName(field);
        ColumnLength length = new ColumnLength(field);

        if (field.isAnnotationPresent(Id.class)) {
            return new DatabasePrimaryColumn(name, length, field);
        }

        ColumnNullable nullable = ColumnNullable.getInstance(field);
        return new DatabaseColumn(name, field.getType(), length, nullable);
    }

    public String getName() {
        return name;
    }

    public List<DatabaseColumn> getColumns() {
        return columns;
    }
}
