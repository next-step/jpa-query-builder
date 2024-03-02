package persistence.sql.dml.model;

import persistence.sql.ColumnUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public class Where {

    private static final String AND_OPERATOR = " AND ";

    private final Class<?> clz;

    private final Object entity;

    public Where(Class<?> clz) {
        this(clz, null);
    }

    public Where(Object entity) {
        this(entity.getClass(), entity);
    }

    public Where(Class<?> clz, Object entity) {
        this.clz = clz;
        this.entity = entity;
    }

    public String id(Object id) {
        return Arrays.stream(clz.getDeclaredFields())
                .filter(ColumnUtils::isId)
                .findAny()
                .map(Field::getName)
                .orElseThrow(() -> new IllegalArgumentException(id + " field not exist!"));
    }

    public String entity(Value value) {
        return Arrays.stream(clz.getDeclaredFields())
                .filter(ColumnUtils::includeColumn)
                .map(field -> value.clause(field, entity))
                .filter(Objects::nonNull)
                .collect(Collectors.joining(AND_OPERATOR));
    }
}
