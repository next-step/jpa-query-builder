package persistence.model;

import jakarta.persistence.Transient;
import persistence.sql.dialect.Dialect;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EntityTableColumns {
    private final List<EntityColumn> columns;

    private EntityTableColumns(List<EntityColumn> columns) {
        this.columns = columns;
    }

    public static EntityTableColumns build(Class<?> entityClass, Dialect dialect) {
        List<EntityColumn> columns = Arrays.stream(entityClass.getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .map(field -> EntityColumn.build(field, dialect))
                .toList();

        return new EntityTableColumns(columns);
    }

    public List<EntityColumn> getAll() {
        return columns;
    }

    public List<EntityColumn> getPrimaryColumns() {
        return columns.stream()
                .filter(EntityColumn::isPrimary)
                .collect(Collectors.toList());
    }
}
