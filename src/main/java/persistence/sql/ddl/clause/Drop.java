package persistence.sql.ddl.clause;

import persistence.sql.ddl.dialect.Dialect;
import persistence.sql.meta.table.TableName;

public class Drop {
    private final TableName tableName;
    private final Dialect dialect;

    public Drop(Class<?> clazz, Dialect dialect) {
        this.tableName = new TableName(clazz);
        this.dialect = dialect;
    }

    public String getTableName() {
        return tableName.getName();
    }
}
