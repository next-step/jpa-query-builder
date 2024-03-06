package persistence.sql.dml.clause;

import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import util.ClauseUtil;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

public class ValueClause {

    private final Object entity;

    public ValueClause(Object entity) {
        this.entity = entity;
    }

    public String getValueClause() {
        return Arrays.stream(entity.getClass().getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Id.class))
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .map(this::getFieldValue)
                .collect(Collectors.joining(", "));
    }

    private String getFieldValue(Field field) {
        field.setAccessible(true);

        try {
            String value = field.get(entity).toString();
            return ClauseUtil.addQuotesWhenRequire(field.getType(), value);
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException("not access " + field.getName());
        }
    }
}
