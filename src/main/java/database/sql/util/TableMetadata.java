package database.sql.util;

import jakarta.persistence.Table;

public class TableMetadata {

    private final Class<?> entityClass;

    public TableMetadata(Class<?> entityClass) {
        this.entityClass = entityClass;
    }

    public String getTableName() {
        Table tableAnnotation = entityClass.getAnnotation(Table.class);
        if (tableAnnotation != null && !tableAnnotation.name().isEmpty()) {
            return tableAnnotation.name();
        }
        return entityClass.getSimpleName();
    }
}
