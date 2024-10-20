package builder.dml.builder;

import builder.dml.DMLBuilderData;

public class UpdateQueryBuilder {

    private final static String UPDATE_BY_ID_QUERY = "UPDATE {tableName} SET {columnDefinitions} WHERE {entityPkName} = {values};";
    private final static String TABLE_NAME = "{tableName}";
    private final static String VALUES = "{values}";
    private final static String ENTITY_PK_NAME = "{entityPkName}";
    private final static String COLUMN_DEFINITIONS = "{columnDefinitions}";

    public String buildQuery(DMLBuilderData dmlBuilderData) {
        return updateByIdQuery(dmlBuilderData);
    }

    //update 쿼리를 생성한다.
    private String updateByIdQuery(DMLBuilderData dmlBuilderData) {
        // 최종 SQL 쿼리 생성
        return UPDATE_BY_ID_QUERY.replace(TABLE_NAME, dmlBuilderData.getTableName())
                .replace(COLUMN_DEFINITIONS, dmlBuilderData.getColumnDefinitions())
                .replace(ENTITY_PK_NAME, dmlBuilderData.getPkName())
                .replace(VALUES, String.valueOf(dmlBuilderData.wrapString()));
    }
}
