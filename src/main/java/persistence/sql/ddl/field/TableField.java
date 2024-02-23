package persistence.sql.ddl.field;

public class TableField {
    private final String tableName;

    public TableField(String tableName) {
        this.tableName = tableName;
    }

    public String toSQL() {
        return "CREATE TABLE " + tableName;
    }
}
