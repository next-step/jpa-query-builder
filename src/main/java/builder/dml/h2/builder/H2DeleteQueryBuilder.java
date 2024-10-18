package builder.dml.h2.builder;

import builder.dml.DMLColumnData;
import builder.dml.DMLQueryBuilder;
import builder.dml.h2.H2DMLBuilder;
import util.StringUtil;

import java.util.List;

public class H2DeleteQueryBuilder implements DMLQueryBuilder {

    private final static String DELETE_BY_ID_QUERY = "DELETE FROM {tableName} WHERE {entityPkName} = {values};";
    private final static String TABLE_NAME = "{tableName}";
    private final static String VALUES = "{values}";
    private final static String ENTITY_PK_NAME = "{entityPkName}";

    @Override
    public String buildQuery(String tableName, List<DMLColumnData> columns, Object... id) {
        return deleteByIdQuery(tableName, columns, id);
    }

    //delete 쿼리문을 생성한다.
    private String deleteByIdQuery(String tableName, List<DMLColumnData> columns, Object... id) {
        Object primaryKeyValue = id != null && id.length > 0 ? id[0] : null;
        //데이터 타입이 String 이면 작은 따옴표로 묶어준다.
        if (primaryKeyValue instanceof String) {
            primaryKeyValue = StringUtil.wrapSingleQuote(primaryKeyValue);
        }

        return DELETE_BY_ID_QUERY.replace(TABLE_NAME, tableName)
                .replace(ENTITY_PK_NAME, H2DMLBuilder.getPkName(columns))
                .replace(VALUES, String.valueOf(primaryKeyValue));
    }

}
