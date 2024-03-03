package persistence.sql.dml.model;

import persistence.sql.ColumnUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public class Where {

    private static final String AND_OPERATOR = " AND ";

    private final Class<?> clz;

    public Where(Object entity) {
        this(entity.getClass());
    }

    public Where(Class<?> clz) {
        this.clz = clz;
    }

    public String getIdClause() {
        return Arrays.stream(clz.getDeclaredFields())
                .filter(ColumnUtils::isId)
                .findAny()
                .map(Field::getName)
                .orElseThrow(() -> new IllegalArgumentException("field not exist!"));
    }

    public String getEntityClause(Value value) {
        return Arrays.stream(clz.getDeclaredFields())
                .filter(ColumnUtils::includeColumn)
                .map(value::clause)
                .filter(Objects::nonNull)
                .collect(Collectors.joining(AND_OPERATOR));
    }
}
