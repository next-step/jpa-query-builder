package persistence.sql.ddl;

import java.util.List;

public record TableInfo(String tableName, List<TableColumn> columns) {

    public PrimaryKey primaryKey() {
        for (TableColumn column : columns) {
            if (column.isPrimaryKey()) {
                return column.primaryKey();
            }
        }

        throw new IllegalArgumentException("Primary key not found");
    }
}
