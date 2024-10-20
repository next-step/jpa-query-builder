package builder.dml.builder;

import builder.dml.DMLBuilderData;

public class SelectAllQueryBuilder {

    private final static String FIND_ALL_QUERY = "SELECT {columnNames} FROM {tableName};";
    private final static String TABLE_NAME = "{tableName}";
    private final static String COLUMN_NAMES = "{columnNames}";

    public String buildQuery(DMLBuilderData dmlBuilderData) {
        return findAllQuery(dmlBuilderData);
    }

    //findAll 쿼리문을 생성한다.
    private String findAllQuery(DMLBuilderData dmlBuilderData) {
        return FIND_ALL_QUERY.replace(TABLE_NAME, dmlBuilderData.getTableName())
                .replace(COLUMN_NAMES, dmlBuilderData.getColumnNames());
    }

}
