package persistence.sql.view;

import persistence.sql.exception.IllegalFieldAccessException;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

import static persistence.sql.view.StringConstant.DELIMITER;

public final class ColumnValues {
    private ColumnValues() {}

    public static String render(Object object, List<Field> fields) {
        String values = fields.stream()
                .map(field -> render(object, field))
                .collect(Collectors.joining(DELIMITER));
        return new StringBuilder()
                .append(" VALUES (")
                .append(values)
                .append(")")
                .toString();
    }

    private static String render(Object object, Field field) {
        try {
            field.setAccessible(true);
            return ColumnValue.render(
                    field.get(object)
            );
        } catch (IllegalAccessException e) {
            throw new IllegalFieldAccessException();
        }
    }
}
