package persistence.sql.dml.caluse;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;

import java.lang.reflect.Field;

public class ValueClause {
    private final Object object;
    private final Field field;

    public ValueClause(Object object, Field field) {
        this.object = object;
        this.field = field;
    }

    public String getValue() {
        if (field.isAnnotationPresent(Id.class)) {
            return getPKValue();
        }
        if (field.isAnnotationPresent(Transient.class)) {
            return "";
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
        return "";
    }

    private String getValueByField() {
        try {
            field.setAccessible(true);
            return field.get(object).toString();
        } catch (IllegalAccessException e) {
            throw new RuntimeException("필드의 값에 접근할 수 없습니다.", e);
        }
    }
}
