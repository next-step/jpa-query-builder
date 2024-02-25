package persistence.sql.dml.caluse;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;

import java.lang.reflect.Field;

public class ValueClause {
    public static final String DEFAULT_VALUE = "DEFAULT";
    public static final String STRING_QUOTE = "'";
    private final Object object;
    private final Field field;

    public ValueClause(Object object, Field field) {
        this.validateColumn(field);
        this.object = object;
        this.field = field;
    }

    public String getValue() {
        if (field.isAnnotationPresent(Id.class)) {
            return getPKValue();
        }
        return getValueByField();
    }

    private String getPKValue() {
        GenerationType generationType = field.isAnnotationPresent(GeneratedValue.class)
                ? field.getAnnotation(GeneratedValue.class).strategy()
                : GenerationType.AUTO;
        if (generationType.equals(GenerationType.AUTO)) {
            return getValueByField();
        }
        return DEFAULT_VALUE;
    }

    private String getValueByField() {
        try {
            field.setAccessible(true);
            Class<?> type = field.getType();
            Object value = field.get(object);
            if (type.equals(Integer.class) || type.equals(Long.class)) {
                return value.toString();
            }
            return STRING_QUOTE + value.toString() + STRING_QUOTE;
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
