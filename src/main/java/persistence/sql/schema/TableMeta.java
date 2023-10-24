package persistence.sql.schema;

import jakarta.persistence.Table;

public class TableMeta {

    private final Class<?> type;
    private final String tableName;

    private TableMeta(Class<?> type, String tableName) {
        this.type = type;
        this.tableName = tableName;
    }

    public static TableMeta of(Class<?> entityClazz) {
        if (entityClazz.isAnnotationPresent(Table.class)) {
            final Table tableAnnotation = entityClazz.getAnnotation(Table.class);
            return new TableMeta(entityClazz, tableAnnotation.name().toUpperCase());
        }

        return new TableMeta(entityClazz, entityClazz.getSimpleName().toUpperCase());
    }

    public String getTableName() {
        return tableName;
    }

    public Class<?> getType() {
        return this.type;
    }
}
