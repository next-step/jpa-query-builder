package persistence.sql.dml;

import java.util.List;


public record InsertMetadata(TableName tableName,
                             List<ColumnName> columnNames) {

}
