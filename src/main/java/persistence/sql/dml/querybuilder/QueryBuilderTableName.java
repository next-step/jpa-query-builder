package persistence.sql.dml.querybuilder;

public class QueryBuilderTableName {

    private final String tableName;

    public QueryBuilderTableName(String tableName) {
        validateTableName(tableName);
        this.tableName = tableName;
    }

    public String getName() {
        return tableName;
    }

    private void validateTableName(String tableName) {
        if (tableName == null || tableName.isEmpty()) {
            throw new IllegalArgumentException("Table name cannot be null or empty");
        }
    }

}
