package persistence.sql.ddl;

import jakarta.persistence.Table;

public class TableName {
    private final Class<?> clazz;

    public TableName(final Class<?> clazz) {
        this.clazz = clazz;
    }

    String getTableName(final Class<?> clazz) {
        final Table tableAnnotation = clazz.getAnnotation(Table.class);
        return hasTableName(tableAnnotation) ?
                tableNameFrom(tableAnnotation) :
                tableNameFrom(clazz);
    }

    private boolean hasTableName(final Table tableAnnotation) {
        return tableAnnotation != null && !tableAnnotation.name().isEmpty();
    }

    private String tableNameFrom(final Table tableAnnotation) {
        return tableAnnotation.name().toUpperCase();
    }

    private String tableNameFrom(final Class<?> clazz) {
        return clazz.getSimpleName().toUpperCase();
    }
}
