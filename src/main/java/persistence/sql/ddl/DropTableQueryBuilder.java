package persistence.sql.ddl;

import jakarta.persistence.Entity;

public class DropTableQueryBuilder extends DDLQueryBuilder  {
    public static final String DROP_TABLE = "DROP TABLE ";

    @Override
    public String executeQuery(Class<?> entityClass) {
        return dropTable(entityClass);
    }

    public  String dropTable(Class<?> entityClass) {
        if (!entityClass.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException("This Class is not an Entity ");
        }
        String tableName = CreateTableQueryBuilder.getTableName(entityClass);
        return DROP_TABLE + tableName + ";";
    }
}
