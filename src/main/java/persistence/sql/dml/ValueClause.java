package persistence.sql.dml;

import java.util.List;
import java.util.stream.Collectors;

public class ValueClause<T> {

    private final T entity;

    public ValueClause(T entity) {
        this.entity = entity;
    }

    public String getClause() throws IllegalAccessException {
        List<ColumnValue> columnValues = new ColumnValues<>(entity).getValues();

        return columnValues.stream()
            .map(ColumnValue::toSqlValue)
            .collect(Collectors.joining(", "));
    }

}
