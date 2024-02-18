package persistence.sql.ddl;

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
    private final List<Class<?>> targetClasses;
    private final String defaultValue;

    DataType(List<Class<?>> targetClasses, String defaultValue) {
        this.targetClasses = targetClasses;
        this.defaultValue = defaultValue;
    }

    public static DataType from(Class<?> targetClass) {
        return Arrays.stream(values())
                .filter(it -> it.targetClasses.contains(targetClass))
                .findAny()
                .orElseThrow(NotAllowedDataTypeException::new);
    }

    public boolean containsDefaultValue() {
        return !defaultValue.isBlank();
    }

    public String getDefaultValue() {
        return START_SYMBOL + defaultValue + END_SYMBOL;
    }

    public String getTypeQuery() {
        String query = this.name();
        if (this.containsDefaultValue()) {
            query += this.getDefaultValue();
        }
        return query;
    }
}
