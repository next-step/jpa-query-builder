package persistence.sql.ddl;

import jakarta.persistence.Entity;

public class DropTableQueryBuilder implements QueryBuilderAdapter {
    public static final String DROP_TABLE = "DROP TABLE ";

    @Override
    public String executeQuery(Class<?> entityClass, DDLType ddlType) {
        if (ddlType == DDLType.DROP) {
            return dropTable(entityClass);
        }
        throw new IllegalArgumentException("Unsupported DDL Type");
    }

    public static String dropTable(Class<?> entityClass) {
        if (!entityClass.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException("This Class is not an Entity ");
        }
        String tableName = CreateTableQueryBuilder.getTableName(entityClass);
        return DROP_TABLE + tableName + ";";
    }
}
