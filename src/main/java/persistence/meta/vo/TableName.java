package persistence.meta.vo;

public class TableName {
    private final String className;
    private final String tableName;

    public TableName(String className, String tableName) {
        this.className = className;
        this.tableName = tableName;
    }

    public String getClassName() {
        return className;
    }

    public String getTableName() {
        return tableName;
    }
}
