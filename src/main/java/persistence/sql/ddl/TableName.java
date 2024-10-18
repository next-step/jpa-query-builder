package persistence.sql.ddl;

import jakarta.persistence.Table;

class TableName {
    private final Class<?> clazz;

    TableName(final Class<?> clazz) {
        this.clazz = clazz;
    }

    String value() {
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
