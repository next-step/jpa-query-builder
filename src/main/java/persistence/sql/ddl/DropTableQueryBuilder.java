package persistence.sql.ddl;

import jakarta.persistence.Entity;

public class DropTableQueryBuilder {
    public static final String DROP_TABLE = "DROP TABLE ";

    public static String dropTable(Class<?> entityClass) {
        if (!entityClass.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException("This Class is not an Entity ");
        }
        String tableName = CreateTableQueryBuilder.getTableName(entityClass);
        return DROP_TABLE + tableName + ";";
    }
}