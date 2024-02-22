package persistence.sql.ddl;

public class DDLQueryFormatter {

    public static String createTableQuery(String tableName, String columnClause, String primaryKeyClause) {
        return String.format("CREATE TABLE %s (%s%s)",
            tableName,
            columnClause,
            primaryKeyClause
        );
    }

    public String dropTableQuery(String tableName) {
        return String.format("DROP TABLE %s", tableName);
    }




}
