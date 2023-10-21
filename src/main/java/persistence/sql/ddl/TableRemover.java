package persistence.sql.ddl;

import lombok.Builder;
import persistence.sql.vo.TableName;

public class TableRemover {
    private final TableName tableName;

    @Builder
    private TableRemover(TableName tableName) {
        this.tableName = tableName;
    }

    public TableName getTableName() {
        return tableName;
    }
}
