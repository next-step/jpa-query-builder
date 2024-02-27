package persistence.sql.dml.clause;

import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import persistence.sql.meta.pk.GenerationType;

import java.lang.reflect.Field;

public class ValueClause {
    private static final String DEFAULT_VALUE = "DEFAULT";
    private static final String SINGLE_QUOTE = "'";
    private final String value;

    public ValueClause(Object object, Field field) {
        this.validateColumn(field);
        this.value = extractValue(object, field);
    }

    public String getValue() {
        return this.value;
    }

    private String extractValue(Object object, Field field) {
        if (field.isAnnotationPresent(Id.class)) {
            return extractValueByPK(object, field);
        }
        return extractValueByField(object, field);
    }

    private String extractValueByPK(Object object, Field field) {
        GenerationType generationType = GenerationType.of(object.getClass());
        if (generationType.equals(GenerationType.AUTO)) {
            return extractValueByField(object, field);
        }
        return DEFAULT_VALUE;
    }

    private String extractValueByField(Object object, Field field) {
        try {
            field.setAccessible(true);
            Class<?> type = field.getType();
            Object value = field.get(object);
            if (type.equals(Integer.class) || type.equals(Long.class)) {
                return value.toString();
            }
            return SINGLE_QUOTE + value.toString() + SINGLE_QUOTE;
        } catch (IllegalAccessException e) {
            throw new RuntimeException("필드의 값에 접근할 수 없습니다.", e);
        }
    }

    private void validateColumn(Field field) {
        if (field.isAnnotationPresent(Transient.class)) {
            throw new IllegalArgumentException(String.format("%s has Transient Annotation Can not Field", field.getName()));
        }
    }
}
