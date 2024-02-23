package persistence.sql.domain;

import persistence.exception.NotAllowedDataTypeException;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public enum DataType {
    BIGINT(List.of(Long.class), new EnumMap<>(Map.of(Dialect.H2, "BIGINT"))),
    VARCHAR(List.of(String.class), new EnumMap<>(Map.of(Dialect.H2, "VARCHAR"))),
    INTEGER(List.of(int.class, Integer.class), new EnumMap<>(Map.of(Dialect.H2, "INTEGER"))),
    ;

    private final List<Class<?>> targets;
    private final Map<Dialect, String> dialects;

    DataType(List<Class<?>> targets, Map<Dialect, String> dialects) {
        this.targets = targets;
        this.dialects = dialects;
    }

    public static DataType from(Class<?> target) {
        return Arrays.stream(values())
                .filter(it -> it.targets.contains(target))
                .findAny()
                .orElseThrow(NotAllowedDataTypeException::new);
    }

    public String getDataTypeForDialect(Dialect dialect) {
        return dialects.get(dialect);
    }

    public boolean isVarchar() {
        return this == VARCHAR;
    }
}
