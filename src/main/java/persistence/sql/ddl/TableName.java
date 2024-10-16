package persistence.sql.ddl;

import jakarta.persistence.Table;

public class TableName {
    private final Class<?> clazz;

    public TableName(final Class<?> clazz) {
        this.clazz = clazz;
    }

    String getTableName(final Class<?> clazz) {
        final Table tableAnnotation = clazz.getAnnotation(Table.class);
        if (tableAnnotation != null && !tableAnnotation.name().isEmpty()) {
            return tableAnnotation.name().toUpperCase();
        }
        return clazz.getSimpleName().toUpperCase();
    }
}
