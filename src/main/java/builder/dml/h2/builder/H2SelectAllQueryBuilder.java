package builder.dml.h2.builder;

import builder.dml.DMLColumnData;
import builder.dml.DMLQueryBuilder;

import java.util.List;
import java.util.stream.Collectors;

public class H2SelectAllQueryBuilder implements DMLQueryBuilder {

    private final static String FIND_ALL_QUERY = "SELECT {columnNames} FROM {tableName};";
    private final static String COMMA = ", ";
    private final static String TABLE_NAME = "{tableName}";
    private final static String COLUMN_NAMES = "{columnNames}";

    @Override
    public String buildQuery(String tableName, List<DMLColumnData> columns, Object... id) {
        return findAllQuery(tableName, columns);
    }

    //findAll 쿼리문을 생성한다.
    private String findAllQuery(String tableName, List<DMLColumnData> columns) {
        //컬럼명을 가져온다.
        String columnNames = columns.stream()
                .map(DMLColumnData::getColumnName)
                .collect(Collectors.joining(COMMA));

        return FIND_ALL_QUERY.replace(TABLE_NAME, tableName)
                .replace(COLUMN_NAMES, columnNames);
    }

}
