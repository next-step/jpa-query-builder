package persistence.sql.dml;

import java.util.List;


public record InsertMetadata(String tableName,
                             List<String> columnNames) {

}
