package builder.dml.builder;

import builder.dml.DMLBuilderData;

public class DeleteQueryBuilder {

    private final static String DELETE_BY_ID_QUERY = "DELETE FROM {tableName} WHERE {entityPkName} = {values};";
    private final static String TABLE_NAME = "{tableName}";
    private final static String VALUES = "{values}";
    private final static String ENTITY_PK_NAME = "{entityPkName}";

    public String buildQuery(DMLBuilderData dmlBuilderData) {
        return deleteByIdQuery(dmlBuilderData);
    }

    //delete 쿼리문을 생성한다.
    private String deleteByIdQuery(DMLBuilderData dmlBuilderData) {
        return DELETE_BY_ID_QUERY.replace(TABLE_NAME, dmlBuilderData.getTableName())
                .replace(ENTITY_PK_NAME, dmlBuilderData.getPkName())
                .replace(VALUES, String.valueOf(dmlBuilderData.wrapString()));
    }

}
