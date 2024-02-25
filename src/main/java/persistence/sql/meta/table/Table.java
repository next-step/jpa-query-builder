package persistence.sql.meta.table;

import jakarta.persistence.Entity;

public class Table {
    private final TableName tableName;

    public Table(Class<?> clazz) {
        this.validateTable(clazz);
        this.tableName = new TableName(clazz);
    }

    private void validateTable(Class<?> clazz) {
        if (!clazz.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException(String.format("%s has not Entity annotations", clazz.getName()));
        }
    }

    public String getTableName() {
        return tableName.getName();
    }
}
