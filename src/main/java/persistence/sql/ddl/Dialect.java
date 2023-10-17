package persistence.sql.ddl;

import jakarta.persistence.GenerationType;

public abstract class Dialect {
    public abstract String getVarchar();
    public abstract String getInteger();
    public abstract String getBigInt();
    public abstract String getGeneratedType(GenerationType generationType);
    public abstract String notNull();
    public abstract String primaryKey(String columnName);
    public String dropTable(String tableName) {
        return "DROP TABLE" + tableName;
    }
    public String createTablePreFix(String tableName) {
        return "CREATE TABLE " + tableName;
    }
}
