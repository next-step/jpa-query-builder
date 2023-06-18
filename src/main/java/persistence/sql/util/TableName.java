package persistence.sql.util;

import jakarta.persistence.Table;

public final class TableName {
    private TableName() {}

    public static <T> String build(Class<T> clazz) {
        Table table = clazz.getAnnotation(Table.class);
        String tableName = table == null || table.name().isBlank()
                ? clazz.getSimpleName()
                : table.name();
        return tableName.toLowerCase();
    }
}
