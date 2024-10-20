package builder.ddl.builder;

import builder.ddl.DDLBuilderData;

public class DropQueryBuilder {

    private final static String DROP_QUERY = "DROP TABLE {tableName};";
    private final static String TABLE_NAME = "{tableName}";

    public String buildQuery(DDLBuilderData ddlBuilderData) {
        return dropTableQuery(ddlBuilderData);
    }

    //Drop 쿼리 생성
    private String dropTableQuery(DDLBuilderData ddlBuilderData) {
        return DROP_QUERY.replace(TABLE_NAME, ddlBuilderData.getTableName());
    }
}
