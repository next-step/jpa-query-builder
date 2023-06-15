package persistence.sql.common;

import persistence.sql.exception.IllegalFieldAccessException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static persistence.sql.common.StringConstant.DELIMITER;

public class ColumnValues {
    private final List<Object> values = new ArrayList<>();

    public static ColumnValues of(Object object, ColumnFields columnFields) {
        return columnFields.stream().reduce(
                new ColumnValues(),
                (columnValues, field) -> columnValues.addValue(object, field),
                (__, columnValues) -> columnValues
        );
    }

    private ColumnValues addValue(Object object, Field field) {
        try {
            field.setAccessible(true);
            values.add(field.get(object));
        } catch (IllegalAccessException e) {
            throw new IllegalFieldAccessException();
        }
        return this;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append(" VALUES (")
                .append(joining())
                .append(")")
                .toString();
    }

    private String joining() {
        return values.stream()
                .map(Object::toString)
                .collect(Collectors.joining(DELIMITER));
    }
}
