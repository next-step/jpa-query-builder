package persistence.sql;

import persistence.sql.domain.ColumnOperation;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MySqlDialect extends Dialect {

    private final Map<Integer, String> jdbcTypesToString;
    private final Map<Integer, Integer> defaultJdbcTypeSize;

    public MySqlDialect() {
        jdbcTypesToString = initJdbcTypeToString();
        defaultJdbcTypeSize = initDefaultJdbcTypeSize();
    }

    private Map<Integer, String> initJdbcTypeToString() {
        Map<Integer, String> jdbcTypesToString = new HashMap<>();
        jdbcTypesToString.put(Types.BIGINT, "BIGINT");
        jdbcTypesToString.put(Types.INTEGER, "INT");
        jdbcTypesToString.put(Types.VARCHAR, "VARCHAR(%s)");
        return jdbcTypesToString;
    }

    private Map<Integer, Integer> initDefaultJdbcTypeSize() {
        Map<Integer, Integer> defaultJdbcTypeSize = new HashMap<>();
        defaultJdbcTypeSize.put(Types.BIGINT, null);
        defaultJdbcTypeSize.put(Types.INTEGER, null);
        defaultJdbcTypeSize.put(Types.VARCHAR, 255);
        return defaultJdbcTypeSize;
    }

    @Override
    public String getJdbcTypeFromJavaClass(ColumnOperation column) {
        Class<?> clazz = column.getColumnObjectType();
        Integer size = column.getColumnSize();

        return Optional.ofNullable(javaClassToJdbcType.get(clazz))
                .map(jdbcType -> {
                    String typeValue = jdbcTypesToString.get(jdbcType);
                    Integer typeSize = getSize(jdbcType, size);
                    return String.format(typeValue, typeSize);
                })
                .orElseThrow(() -> new IllegalArgumentException("jdbc type of " + clazz + " does not defined in Dialect"));
    }

    private Integer getSize(Integer jdbcType, Integer size) {
        if (size != null) {
            return size;
        }
        return defaultJdbcTypeSize.get(jdbcType);
    }

}
