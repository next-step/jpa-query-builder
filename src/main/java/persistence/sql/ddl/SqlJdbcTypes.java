package persistence.sql.ddl;

import persistence.sql.ddl.exception.NotSupportException;

import java.sql.Types;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public enum SqlJdbcTypes {
    VARCHAR(Types.VARCHAR, String.class),
    INTEGER(Types.INTEGER, Integer.class, int.class),
    BIGINT(Types.BIGINT, Long.class, long.class);

    private static final Map<Class<?>, Integer> javaTypeToSqlTypeMap =
        Arrays.stream(SqlJdbcTypes.values())
            .flatMap(it -> it.classes.stream().map(clazz -> Map.entry(clazz, it.types)))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    private final int types;
    private final List<Class<?>> classes;

    SqlJdbcTypes(int types, Class<?>... clazz) {
        this.types = types;
        this.classes = Arrays.stream(clazz).toList();
    }

    public static <T> Integer typeOf(Class<T> clazz) {
        if (!javaTypeToSqlTypeMap.containsKey(clazz)) {
            throw new NotSupportException();
        }

        return javaTypeToSqlTypeMap.get(clazz);
    }
}
