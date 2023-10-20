package persistence.sql.common;

import jakarta.persistence.Transient;

import java.lang.reflect.Field;
import java.util.Arrays;

class Value {
    private final Object value;

    private Value(Object object) {
        this.value = object;
    }

    protected static <T> Value[] of(T t) {
        Field[] fields = t.getClass().getDeclaredFields();
        return Arrays.stream(fields)
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .map(field -> new Value(extractValue(t, field)))
                .toArray(Value[]::new);
    }

    private static <T> Object extractValue(T t, Field field) {
        try {
            Field fi = t.getClass().getDeclaredField(field.getName());

            fi.setAccessible(true);

            return fi.get(t);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * value가 문자열인 경우 콜론과 함께 반환합니다.
     * 예) 'apple', '1'
     */
    private String parseChar() {
        String v = value.toString();
        String type = value.getClass().getSimpleName();

        if(!value.toString().matches("[-+]?\\d*\\.?\\d+")) {
            v = String.format("'%s'", value);
        }

        if(type.equals("String") || type.equals("char") || type.equals("Character")) {
            v = String.format("'%s'", value);
        }

        return v;
    }

    public String getValue() {
        if(value == null) {
            return null;
        }

        return parseChar();
    }
}
