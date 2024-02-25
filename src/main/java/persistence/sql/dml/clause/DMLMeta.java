package persistence.sql.dml.clause;

import persistence.sql.meta.table.TableName;

public class DMLMeta {
    private final TableName tableName;
    private final PKClause pkClause;
    private final ColumnsClause columnsClause;

    public DMLMeta(Class<?> clazz) {
        this.tableName = new TableName(clazz);
        this.pkClause = new PKClause(clazz);
        this.columnsClause = new ColumnsClause(clazz);
    }

    public String getTableName() {
        return tableName.getName();
    }

    public String getColumns() {
        return columnsClause.getColumns();
    }

    public String getPKName() {
        return pkClause.getName();
    }
}
