package persistence.sql.dml.caluse;

import jakarta.persistence.Id;
import jakarta.persistence.Transient;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ColumnsClause {
    private final List<ColumnClause> columns;
    private final Field[] fields;

    public ColumnsClause(Class<?> clazz) {
        this.columns = columnClauses(clazz);
        this.fields = clazz.getDeclaredFields();
    }

    private List<ColumnClause> columnClauses(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .map(ColumnClause::new)
                .collect(Collectors.toList());
    }


    public String getColumns() {
        StringBuilder sb = new StringBuilder();
        columns.forEach(column -> {
            if (column.getColumnName().isBlank()) {
                return;
            }
            sb.append(column.getColumnName());
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
