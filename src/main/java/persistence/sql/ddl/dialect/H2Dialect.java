package persistence.sql.ddl.dialect;

import persistence.sql.metadata.ColumnOption;

import java.util.Map;

public class H2Dialect implements Dialect {

    private static final Map<Class<?>, String> JAVA_TO_SQL_TYPES = Map.of(
            Long.class, "bigint",
            Integer.class, "integer",
            String.class, "varchar(255)"
    );

    private static final String AUTO_INCREMENT = "auto_increment";
    private static final String NOT_NULL = "not null";

    @Override
    public String getSqlType(Class<?> columnType) {
        if (!JAVA_TO_SQL_TYPES.containsKey(columnType)) {
            throw new IllegalArgumentException("지원하지 않는 컬럼 타입: " + columnType);
        }

        return JAVA_TO_SQL_TYPES.get(columnType);
    }

    @Override
    public String getClause(ColumnOption option) {
        return switch (option) {
            case IDENTITY -> AUTO_INCREMENT;
            case NOT_NULL -> NOT_NULL;
        };
    }
}
