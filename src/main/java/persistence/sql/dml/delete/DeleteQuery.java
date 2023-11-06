package persistence.sql.dml.delete;

import persistence.sql.vo.TableName;

public class DeleteQuery {
    private final TableName tableName;

    public DeleteQuery(TableName tableName) {
        this.tableName = tableName;
    }

    public TableName getTableName() {
        return tableName;
    }
}
