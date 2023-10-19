package persistence.sql.ddl;

import lombok.Builder;
import persistence.sql.ddl.vo.DatabaseFields;
import persistence.sql.ddl.vo.TableName;

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
