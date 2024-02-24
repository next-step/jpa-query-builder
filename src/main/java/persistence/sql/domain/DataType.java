package persistence.sql.domain;

import persistence.exception.NotAllowedDataTypeException;

import java.util.Arrays;
import java.util.List;

public enum DataType {
    BIGINT(List.of(Long.class)),
    VARCHAR(List.of(String.class)),
    INTEGER(List.of(int.class, Integer.class)),
    ;

    private final List<Class<?>> targets;

    DataType(List<Class<?>> targets) {
        this.targets = targets;
    }

    public static DataType from(Class<?> target) {
        return Arrays.stream(values())
                .filter(it -> it.targets.contains(target))
                .findAny()
                .orElseThrow(NotAllowedDataTypeException::new);
    }

    public boolean isVarchar() {
        return this == VARCHAR;
    }
}
