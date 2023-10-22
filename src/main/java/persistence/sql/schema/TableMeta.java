package persistence.sql.schema;

import jakarta.persistence.Table;

public class TableMeta {

    private final String tableName;

    private TableMeta(String tableName) {
        this.tableName = tableName;
    }

    public static TableMeta of(Class<?> entityClazz) {
        if (entityClazz.isAnnotationPresent(Table.class)) {
            final Table tableAnnotation = entityClazz.getAnnotation(Table.class);
            return new TableMeta(tableAnnotation.name().toUpperCase());
        }

        return new TableMeta(entityClazz.getSimpleName().toUpperCase());
    }

    public String getTableName() {
        return tableName;
    }
}
