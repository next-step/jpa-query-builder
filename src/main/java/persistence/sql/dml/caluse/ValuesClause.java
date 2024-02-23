package persistence.sql.dml.caluse;

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
}
