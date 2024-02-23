package persistence.sql.dml.caluse;

import jakarta.persistence.Id;

import java.lang.reflect.Field;
import java.util.Arrays;

public class ColumnsClause {
    private final Field[] fields;

    public ColumnsClause(Class<?> clazz) {
        this.fields = clazz.getDeclaredFields();
    }

    public String getColumns() {
        StringBuilder sb = new StringBuilder();
        Arrays.stream(fields).forEach(field -> {
            String column = new ColumnClause(field).getColumn();
            if (column.isBlank()) {
                return;
            }
            sb.append(column);
            sb.append(", ");
        });
        sb.deleteCharAt(sb.length() - 1);
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    public String getPkName() {
        Field pkField = Arrays.stream(fields)
                .filter(field -> field.isAnnotationPresent(Id.class))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Id 어노테이션은 반드시 존재해야합니다."));

        return pkField.getName();
    }
}
