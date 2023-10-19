package persistence.sql.ddl;

import lombok.Builder;
import lombok.Getter;
import persistence.sql.ddl.vo.TableName;

@Getter
public class TableRemover {
    private final TableName tableName;

    @Builder
    private TableRemover(TableName tableName) {
        this.tableName = tableName;
    }
}
