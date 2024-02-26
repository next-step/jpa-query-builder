package persistence.sql.dml.clause;

import persistence.sql.meta.table.TableName;

public class Insert {
    private final TableName tableName;
    private final ColumnsClause columnsClause;

    public Insert(Class<?> clazz) {
        this.tableName = new TableName(clazz);
        this.columnsClause = new ColumnsClause(clazz);
    }

    public String getTableName() {
        return tableName.getName();
    }

    public String getColumns() {
        return columnsClause.getColumns();
    }
}
