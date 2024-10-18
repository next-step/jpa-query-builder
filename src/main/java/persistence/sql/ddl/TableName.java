package persistence.sql.ddl;

import jakarta.persistence.Table;

public class TableName {

    private final Class<?> entityClass;

    public TableName(Class<?> entityClass) {
        this.entityClass = entityClass;
    }

    public String getTableName() {
        if (entityClass.isAnnotationPresent(Table.class)) {
            Table table = entityClass.getAnnotation(Table.class);
            return table.name();
        } else {
            return entityClass.getSimpleName();
        }
    }
}
