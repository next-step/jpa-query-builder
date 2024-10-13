package persistence.sql.ddl;

import persistence.sql.ddl.definition.ColumnDefinition;

import java.util.Map;

public class H2Dialect implements Dialect {
    private final Map<SqlType, String> typeMap = Map.of(
            SqlType.VARCHAR, "VARCHAR",
            SqlType.BIGINT, "BIGINT",
            SqlType.INTEGER, "INTEGER"
    );

    @Override
    public String translateType(ColumnDefinition columnDefinition) {
        return switch (columnDefinition.type()) {
            case VARCHAR -> typeMap.get(SqlType.VARCHAR) + "(" + columnDefinition.length() + ")";
            case BIGINT -> typeMap.get(SqlType.BIGINT);
            case INTEGER -> typeMap.get(SqlType.INTEGER);
        };
    }
}
