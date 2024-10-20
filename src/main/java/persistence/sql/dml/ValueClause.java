package persistence.sql.dml;

import java.util.List;

public class ValueClause<T> {

    private final T entity;

    public ValueClause(T entity) {
        this.entity = entity;
    }

    public String getClause() throws IllegalAccessException {
        StringBuilder clause = new StringBuilder();

        List<ColumnValue> columnValues = new ColumnValues<>(entity).getValues();
        for (ColumnValue columnValue : columnValues) {
            clause
                .append(columnValue.toSqlValue())
                .append(", ");
        }

        if (clause.length() > 1) {
            clause.setLength(clause.length() - 2);
        }

        return clause.toString();
    }

}
