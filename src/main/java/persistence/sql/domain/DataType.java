package persistence.sql.domain;

import persistence.exception.NotAllowedDataTypeException;

import java.util.Arrays;
import java.util.List;

public enum DataType {
    BIGINT(List.of(Long.class), ""),
    VARCHAR(List.of(String.class), "255"),
    INTEGER(List.of(int.class, Integer.class), ""),
    ;

    private static final String START_SYMBOL = "(";
    private static final String END_SYMBOL = ")";

    private final List<Class<?>> targets;
    private final String defaultValue;

    DataType(List<Class<?>> targets, String defaultValue) {
        this.targets = targets;
        this.defaultValue = defaultValue;
    }

    public static DataType from(Class<?> target) {
        return Arrays.stream(values())
                .filter(it -> it.targets.contains(target))
                .findAny()
                .orElseThrow(NotAllowedDataTypeException::new);
    }

    public String getTypeQuery() {
        String query = this.name();
        if (this.containsDefaultValue()) {
            query += this.getDefaultValue();
        }
        return query;
    }

    public boolean containsDefaultValue() {
        return !defaultValue.isBlank();
    }

    private String getDefaultValue() {
        return START_SYMBOL + defaultValue + END_SYMBOL;
    }

    public boolean isVarchar() {
        return this == VARCHAR;
    }
}
