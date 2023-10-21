package persistence.sql.ddl;

import lombok.Builder;
import persistence.sql.vo.DatabaseFields;
import persistence.sql.vo.TableName;

public class TableCreator {
    private final TableName tableName;
    private final DatabaseFields fields;

    @Builder
    private TableCreator(TableName tableName, DatabaseFields fields) {
        this.tableName = tableName;
        this.fields = fields;
    }

    public TableName getTableName() {
        return tableName;
    }

    public DatabaseFields getFields() {
        return fields;
    }
}
