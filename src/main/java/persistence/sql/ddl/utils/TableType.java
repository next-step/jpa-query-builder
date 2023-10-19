package persistence.sql.ddl.utils;

import jakarta.persistence.Table;

import java.util.Optional;

public class TableType {
    final private Class<?> entity;

    public TableType(final Class<?> entity) {
        this.entity = entity;
    }

    public String getName() {
        return Optional.ofNullable(getTableAnnotation())
                .filter(table -> !table.name().isEmpty())
                .map(Table::name)
                .orElse(entity.getSimpleName());
    }

    private Table getTableAnnotation() {
        if (entity.isAnnotationPresent(Table.class)) {
            return entity.getAnnotation(Table.class);
        }
        return null;
    }
}
