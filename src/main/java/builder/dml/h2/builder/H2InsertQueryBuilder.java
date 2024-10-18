package builder.dml.h2.builder;

import builder.dml.DMLBuilder;
import builder.dml.DMLColumnData;
import builder.dml.DMLQueryBuilder;
import util.StringUtil;

import java.util.List;
import java.util.stream.Collectors;

public class H2InsertQueryBuilder implements DMLQueryBuilder {

    private final static String INSERT_QUERY = "INSERT INTO {tableName} ({columnNames}) VALUES ({values});";
    private final static String COMMA = ", ";
    private final static String TABLE_NAME = "{tableName}";
    private final static String COLUMN_NAMES = "{columnNames}";
    private final static String VALUES = "{values}";

    //insert 쿼리를 생성한다. Insert 쿼리는 인스턴스의 데이터를 받아야함
    @Override
    public String buildQuery(String tableName, List<DMLColumnData> columns, Object... id) {
        return insertQuery(tableName, columns);
    }

    //insert쿼리문을 생성한다.
    private String insertQuery(String tableName, List<DMLColumnData> columns) {
        //컬럼명을 가져온다.
        String columnNames = columns.stream()
                .map(DMLColumnData::getColumnName)
                .collect(Collectors.joining(COMMA));

        //Insert 할 Value 값들을 가져온다.
        String columnValues = columns.stream()
                .map(dmlColumnData -> {
                    Object value = dmlColumnData.getColumnValue();
                    if (dmlColumnData.getColumnType() == String.class) { //데이터 타입이 String 이면 작은 따옴표로 묶어준다.
                        return StringUtil.wrapSingleQuote(value);
                    }
                    return String.valueOf(value);
                })
                .collect(Collectors.joining(COMMA));

        return INSERT_QUERY.replace(TABLE_NAME, tableName)
                .replace(COLUMN_NAMES, columnNames)
                .replace(VALUES, columnValues);
    }

}
