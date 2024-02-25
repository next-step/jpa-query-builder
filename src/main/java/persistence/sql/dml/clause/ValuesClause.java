package persistence.sql.dml.clause;

import jakarta.persistence.Id;
import jakarta.persistence.Transient;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ValuesClause {
    private final Object object;
    private final List<ValueClause> values;

    public ValuesClause(Object object) {
        this.values = valueClauses(object);
        this.object = object;
    }

    private List<ValueClause> valueClauses(Object object) {
        Field[] fields = object.getClass().getDeclaredFields();
        return Arrays.stream(fields)
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .map(field -> new ValueClause(object, field))
                .collect(Collectors.toList());
    }

    public String getValues() {
        StringBuilder sb = new StringBuilder();
        values.forEach(value -> {
            if (value.getValue().isBlank()) {
                return;
            }
            sb.append(value.getValue());
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
