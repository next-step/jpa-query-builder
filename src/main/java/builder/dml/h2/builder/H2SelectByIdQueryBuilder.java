package builder.dml.h2.builder;

import builder.dml.DMLColumnData;
import builder.dml.DMLQueryBuilder;
import builder.dml.h2.H2DMLBuilder;
import util.StringUtil;

import java.util.List;
import java.util.stream.Collectors;

public class H2SelectByIdQueryBuilder implements DMLQueryBuilder {

    private final static String FIND_BY_ID_QUERY = "SELECT {columnNames} FROM {tableName} WHERE {entityPkName} = {values};";
    private final static String COMMA = ", ";
    private final static String TABLE_NAME = "{tableName}";
    private final static String COLUMN_NAMES = "{columnNames}";
    private final static String VALUES = "{values}";
    private final static String ENTITY_PK_NAME = "{entityPkName}";

    @Override
    public String buildQuery(String tableName, List<DMLColumnData> columns, Object... id) {
        return findByIdQuery(tableName, columns, id);
    }

    //findAll 쿼리문을 생성한다.
    private String findByIdQuery(String tableName, List<DMLColumnData> columns, Object... id) {
        Object primaryKeyValue = id != null && id.length > 0 ? id[0] : null;
        //컬럼명을 가져온다.
        String columnNames = columns.stream()
                .map(DMLColumnData::getColumnName)
                .collect(Collectors.joining(COMMA));

        //데이터 타입이 String 이면 작은 따옴표로 묶어준다.
        if (primaryKeyValue instanceof String) {
            primaryKeyValue = StringUtil.wrapSingleQuote(primaryKeyValue);
        }

        return FIND_BY_ID_QUERY.replace(TABLE_NAME, tableName)
                .replace(COLUMN_NAMES, columnNames)
                .replace(ENTITY_PK_NAME, H2DMLBuilder.getPkName(columns))
                .replace(VALUES, String.valueOf(primaryKeyValue));
    }

}
