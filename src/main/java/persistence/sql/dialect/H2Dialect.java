package persistence.sql.dialect;

import java.sql.Types;
import java.util.Map;

public class H2Dialect implements Dialect {

    private final Map<String, Integer> columnTypes = Map.of(
            "STRING", Types.VARCHAR,
            "INTEGER", Types.INTEGER,
            "LONG", Types.BIGINT);

    @Override
    public String createColumnQuery(final String type) {
        final Integer columnType = columnTypes.getOrDefault(type.toUpperCase(), Types.VARCHAR);

        return switch (columnType) {
            case Types.VARCHAR -> "VARCHAR(255)";
            case Types.INTEGER -> "INTEGER";
            case Types.BIGINT -> "BIGINT";
            default -> throw new DialectException("NotFount ColumnType for " + type + "in H2Dialect");
        };
    }

    @Override
    public String getPk() {
        return "PRIMARY KEY";
    }

}
