package builder.dml.builder;

import builder.dml.DMLBuilderData;

public class InsertQueryBuilder {

    private final static String INSERT_QUERY = "INSERT INTO {tableName} ({columnNames}) VALUES ({values});";
    private final static String TABLE_NAME = "{tableName}";
    private final static String COLUMN_NAMES = "{columnNames}";
    private final static String VALUES = "{values}";

    //insert 쿼리를 생성한다. Insert 쿼리는 인스턴스의 데이터를 받아야함
    public String buildQuery(DMLBuilderData dmlBuilderData) {
        return insertQuery(dmlBuilderData);
    }

    //insert쿼리문을 생성한다.
    private String insertQuery(DMLBuilderData dmlBuilderData) {
        return INSERT_QUERY.replace(TABLE_NAME, dmlBuilderData.getTableName())
                .replace(COLUMN_NAMES, dmlBuilderData.getColumnNames())
                .replace(VALUES, dmlBuilderData.getColumnValues());
    }

}
