package persistence.sql.dml.model;

import persistence.sql.ColumnUtils;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Field {

    private final Class<?> clz;

    public Field(Class<?> clz) {
        this.clz = clz;
    }

    private static final String SEPARATOR = ", ";

    public String get() {
        final java.lang.reflect.Field[] fields = clz.getDeclaredFields();

        return Arrays.stream(fields)
                .filter(ColumnUtils::includeColumn)
                .map(ColumnUtils::name)
                .collect(Collectors.joining(SEPARATOR));
    }
}
