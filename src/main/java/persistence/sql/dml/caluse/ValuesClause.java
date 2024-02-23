package persistence.sql.dml.caluse;

import jakarta.persistence.Id;

import java.lang.reflect.Field;
import java.util.Arrays;

public class ValuesClause {
    private final Object object;

    public ValuesClause(Object object) {
        this.object = object;
    }

    public String getValues() {
        Field[] fields = object.getClass().getDeclaredFields();
        StringBuilder sb = new StringBuilder();
        Arrays.stream(fields).forEach(field -> {
            String value = new ValueClause(object, field).getValue();
            if (value.isBlank()) {
                return;
            }
            sb.append(value);
            sb.append(", ");
        });
        sb.deleteCharAt(sb.length() - 1);
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    public String getPkValue() throws IllegalAccessException {
        Field[] fields = object.getClass().getDeclaredFields();
        Field pkField = Arrays.stream(fields)
                .filter(field -> field.isAnnotationPresent(Id.class))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Id 어노테이션은 반드시 존재해야합니다."));

        pkField.setAccessible(true);
        return pkField.get(object).toString();
    }
}
