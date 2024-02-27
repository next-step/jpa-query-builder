package persistence.sql.dml.clause;

import jakarta.persistence.Transient;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ColumnsClause {
    private final List<ColumnClause> columns;

    public ColumnsClause(Class<?> clazz) {
        this.columns = columnClauses(clazz);
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
}
