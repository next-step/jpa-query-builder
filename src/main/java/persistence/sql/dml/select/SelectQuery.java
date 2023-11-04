package persistence.sql.dml.select;

import persistence.sql.vo.TableName;

public class SelectQuery {
    private final TableName tableName;

    public SelectQuery(TableName tableName) {
        this.tableName = tableName;
    }

    public TableName getTableName() {
        return tableName;
    }
}
