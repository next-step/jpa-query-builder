package builder.dml.h2.builder;

import builder.dml.DMLColumnData;
import builder.dml.DMLQueryBuilder;
import builder.dml.h2.H2DMLBuilder;
import util.StringUtil;

import java.util.List;
import java.util.stream.Collectors;

public class H2UpdateQueryBuilder implements DMLQueryBuilder {

    private final static String UPDATE_BY_ID_QUERY = "UPDATE {tableName} SET {columnDefinitions} WHERE {entityPkName} = {values};";
    private final static String COMMA = ", ";
    private final static String TABLE_NAME = "{tableName}";
    private final static String VALUES = "{values}";
    private final static String ENTITY_PK_NAME = "{entityPkName}";
    private final static String COLUMN_DEFINITIONS = "{columnDefinitions}";
    private final static String EQUALS = "=";

    @Override
    public String buildQuery(String tableName, List<DMLColumnData> columns, Object... id) {
        return updateByIdQuery(tableName, columns, id);
    }

    //update 쿼리를 생성한다.
    public String updateByIdQuery(String tableName, List<DMLColumnData> columns, Object... id) {
        Object primaryKeyValue = id != null && id.length > 0 ? id[0] : null;
        // 테이블 열 정의 생성
        String columnDefinitions = columns.stream()
                .filter(column -> !column.isPrimaryKey())
                .map(column -> column.getColumnName() + EQUALS + column.getColumnValueByType())
                .collect(Collectors.joining(COMMA));

        if (primaryKeyValue instanceof String) //데이터 타입이 String 이면 작은 따옴표로 묶어준다.
            primaryKeyValue = StringUtil.wrapSingleQuote(primaryKeyValue);

        // 최종 SQL 쿼리 생성
        return UPDATE_BY_ID_QUERY.replace(TABLE_NAME, tableName)
                .replace(COLUMN_DEFINITIONS, columnDefinitions)
                .replace(ENTITY_PK_NAME, H2DMLBuilder.getPkName(columns))
                .replace(VALUES, String.valueOf(primaryKeyValue));
    }
}
