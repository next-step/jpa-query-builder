package persistence.sql;

import persistence.sql.domain.DatabaseColumn;

import java.sql.Types;
import java.util.Map;
import java.util.Optional;

public class MySqlDialect extends Dialect {

    private final Map<Integer, String> jdbcTypesToString;

    public MySqlDialect() {
        jdbcTypesToString = Map.of(
                Types.BIGINT, "BIGINT",
                Types.INTEGER, "INT",
                Types.VARCHAR, "VARCHAR(%s)"
        );
    }

    @Override
    public String getJdbcTypeFromJavaClass(DatabaseColumn column) {
        Class<?> clazz = column.getColumnObjectType();
        Integer size = column.getSize();

        return Optional.ofNullable(javaClassToJdbcType.get(clazz))
                .map(jdbcTypesToString::get)
                .map(jdbcType -> String.format(jdbcType, size))
                .orElseThrow(IllegalArgumentException::new);
    }

}
