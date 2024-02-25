package persistence.sql.dml.caluse;

import persistence.sql.meta.table.TableName;

public class TableClause {
    private final TableName tableName;

    public TableClause(Class<?> clazz) {
        this.tableName = new TableName(clazz);
    }

    public String getTableName() {
        return tableName.getName();
    }
}
