package persistence.sql.ddl.dto.db;

import java.util.List;

// TODO: validation
public class CreateTableComponent {

    private final TableName tableName;
    private final List<DBColumn> dbColumns;

    public CreateTableComponent(TableName tableName, List<DBColumn> dbColumns) {
        this.tableName = tableName;
        this.dbColumns = dbColumns;
    }

    public String getTableName() {
        return tableName.getName();
    }

    public List<DBColumn> getDBColumns() {
        return dbColumns;
    }

    public int getColumnCount() {
        return dbColumns.size();
    }
}
