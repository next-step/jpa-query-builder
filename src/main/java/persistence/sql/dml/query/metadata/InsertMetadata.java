package persistence.sql.dml.query.metadata;

import java.util.List;


public record InsertMetadata(TableName tableName,
                             List<ColumnName> columnNames) {

}
