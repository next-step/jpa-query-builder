package persistence.sql.dml.clause;

import persistence.sql.meta.table.TableName;

public class Delete {
    private final TableName tableName;
    private final PKClause pkClause;

    public Delete(Class<?> clazz) {
        this.tableName = new TableName(clazz);
        this.pkClause = new PKClause(clazz);
    }

    public String getTableName() {
        return tableName.getName();
    }

    public String getPKName() {
        return pkClause.getName();
    }
}
