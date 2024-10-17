package persistence.sql.dml.query.metadata;

import java.util.List;

public record SelectMetadata(TableName tableName,
                             List<ColumnName> columnNames,
                             List<WhereCondition> whereConditions) {

    public SelectMetadata(TableName tableName,
            List<ColumnName> columnNames) {
        this(tableName, columnNames, List.of());
    }

}
