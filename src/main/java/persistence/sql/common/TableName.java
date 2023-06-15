package persistence.sql.common;

import jakarta.persistence.Table;

public class TableName<T> {
    private final Class<T> clazz;

    public TableName(Class<T> clazz) {this.clazz = clazz;}

    @Override
    public String toString() {
        Table table = clazz.getAnnotation(Table.class);
        String tableName = table == null || table.name().isBlank()
                ? clazz.getSimpleName()
                : table.name();
        return tableName.toLowerCase();
    }
}
