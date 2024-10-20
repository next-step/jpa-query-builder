package builder.dml.builder;

import builder.dml.DMLBuilderData;

public class SelectByIdQueryBuilder {

    private final static String FIND_BY_ID_QUERY = "SELECT {columnNames} FROM {tableName} WHERE {entityPkName} = {values};";
    private final static String TABLE_NAME = "{tableName}";
    private final static String COLUMN_NAMES = "{columnNames}";
    private final static String VALUES = "{values}";
    private final static String ENTITY_PK_NAME = "{entityPkName}";

    public String buildQuery(DMLBuilderData dmlBuilderData) {
        return findByIdQuery(dmlBuilderData);
    }

    //findAll 쿼리문을 생성한다.
    private String findByIdQuery(DMLBuilderData dmlBuilderData) {
        return FIND_BY_ID_QUERY.replace(TABLE_NAME, dmlBuilderData.getTableName())
                .replace(COLUMN_NAMES, dmlBuilderData.getColumnNames())
                .replace(ENTITY_PK_NAME, dmlBuilderData.getPkName())
                .replace(VALUES, String.valueOf(dmlBuilderData.wrapString()));
    }

}
