package persistence.sql.dml.clause;

import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import util.StringUtil;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.stream.Collectors;

public class ValueClause {

    private ValueClause() {
    }

    public static String getValueClause(Object entity) {
        return Arrays.stream(entity.getClass().getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Id.class))
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .map(field -> getValueClause(field, entity))
                .collect(Collectors.joining(", "));
    }

    private static String getValueClause(Field field, Object entity) {
        field.setAccessible(true);

        try {
            String value = field.get(entity).toString();
            return addQuotesWhenString(field.getType(), value);
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException("not access " + field.getName());
        }
    }

    private static String addQuotesWhenString(Class<?> type, String value) {
        if (type == String.class || type == LocalDate.class) {
            return StringUtil.addStringOnBothSides(value, "'");
        }

        return value;
    }
}
