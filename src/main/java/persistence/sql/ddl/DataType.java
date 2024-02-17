package persistence.sql.ddl;

import persistence.exception.NotAllowedDataTypeException;

import java.util.Arrays;
import java.util.List;

public enum DataType {
    BIGINT(List.of(Long.class)),
    VARCHAR(List.of(String.class)),
    INTEGER(List.of(int.class, Integer.class)),
    ;

    private final List<Class<?>> targetClasses;

    DataType(List<Class<?>> targetClasses) {
        this.targetClasses = targetClasses;
    }

    public static DataType from(Class<?> targetClass) {
        return Arrays.stream(values())
                .filter(it -> it.targetClasses.contains(targetClass))
                .findAny()
                .orElseThrow(NotAllowedDataTypeException::new);
    }

    public boolean isVarchar() {
        return this == VARCHAR;
    }
}
