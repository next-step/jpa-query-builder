package persistence.sql.domain.dialect;

import persistence.sql.domain.DataType;

import java.util.HashMap;
import java.util.Map;

public class H2Dialect implements Dialect {
    private static final Map<DataType, String> MAP;
    private static final String CREATE_QUERY_TEMPLATE = "CREATE TABLE %s (%s)";
    private static final String DROP_QUERY_TEMPLATE = "DROP TABLE %s";

    static {
        MAP = new HashMap<>();
        MAP.put(DataType.VARCHAR, "VARCHAR");
        MAP.put(DataType.BIGINT, "BIGINT");
        MAP.put(DataType.INTEGER, "INTEGER");
    }

    public H2Dialect() {
    }

    @Override
    public String convertClassForDialect(DataType dataType) {
        return MAP.get(dataType);
    }

    @Override
    public String getCreateQueryTemplate() {
        return CREATE_QUERY_TEMPLATE;
    }

    @Override
    public String getDropQueryTemplate() {
        return DROP_QUERY_TEMPLATE;
    }
}
