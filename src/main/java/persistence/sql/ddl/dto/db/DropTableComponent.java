package persistence.sql.ddl.dto.db;

public class DropTableComponent {

    private final TableName tableName;

    public DropTableComponent(TableName tableName) {
        this.tableName = tableName;
    }

    public String getTableName() {
        return tableName.getName();
    }
}
