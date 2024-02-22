package persistence.sql.dml.caluse;

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
}
