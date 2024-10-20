package builder.ddl.builder;

import builder.ddl.DDLBuilderData;

public class CreateQueryBuilder {

    private final static String CREATE_QUERY = "CREATE TABLE {tableName} ({columnDefinitions});";
    private final static String TABLE_NAME = "{tableName}";
    private final static String COLUMN_DEFINITIONS = "{columnDefinitions}";

    public String buildQuery(DDLBuilderData ddlBuilderData) {
        return createTableQuery(ddlBuilderData);
    }

    //create 쿼리를 생성한다.
    private String createTableQuery(DDLBuilderData ddlBuilderData) {
        // 최종 SQL 쿼리 생성
        return CREATE_QUERY.replace(TABLE_NAME, ddlBuilderData.getTableName())
                .replace(COLUMN_DEFINITIONS, ddlBuilderData.getColumnDefinitions());
    }
}
