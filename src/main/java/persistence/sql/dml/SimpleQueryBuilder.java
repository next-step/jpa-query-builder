package persistence.sql.dml;

import static persistence.sql.dml.QueryOperation.DELETE;
import static persistence.sql.dml.QueryOperation.INSERT;
import static persistence.sql.dml.QueryOperation.SELECT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.Getter;

@Getter
public class SimpleQueryBuilder {

    private String tableName;
    private final List<String> columns;
    private final List<String> conditions;
    private final List<String> values;
    private QueryOperation operation;

    public SimpleQueryBuilder() {
        this.columns = new ArrayList<>();
        this.conditions = new ArrayList<>();
        this.values = new ArrayList<>();
        this.operation = SELECT;
    }

    public SimpleQueryBuilder select(String... columns) {
        this.columns.addAll(Arrays.asList(columns));
        this.operation = SELECT;
        return this;
    }

    public SimpleQueryBuilder delete() {
        this.operation = DELETE;
        return this;
    }

    public SimpleQueryBuilder insertInto(String tableName) {
        this.operation = INSERT;
        this.tableName = tableName;
        return this;
    }

    public SimpleQueryBuilder columns(String... columns) {
        this.columns.addAll(Arrays.asList(columns));
        return this;
    }

    public SimpleQueryBuilder values(String... values) {
        this.values.addAll(Arrays.asList(values));
        return this;
    }

    public SimpleQueryBuilder from(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public SimpleQueryBuilder where(String condition) {
        this.conditions.add(condition);
        return this;
    }

    public SimpleQueryBuilder and(String condition) {
        if (!this.conditions.isEmpty()) {
            this.conditions.add("AND " + condition);
        } else {
            where(condition);
        }
        return this;
    }

    public SimpleQueryBuilder or(String condition) {
        if (!this.conditions.isEmpty()) {
            this.conditions.add("OR " + condition);
        } else {
            where(condition);
        }
        return this;
    }

    public String build() {
        if (tableName == null || tableName.isEmpty()) {
            throw new IllegalStateException("Table name must be specified.");
        }

        StringBuilder query = new StringBuilder();
        return operation.buildQuery(this, query);


//        switch (operation) {
//            case SELECT:
//                String columnsPart = columns.isEmpty() ? "*" : String.join(", ", columns);
//                query
//                    .append("SELECT ")
//                    .append(columnsPart)
//                    .append(" FROM ")
//                    .append(tableName);
//                break;
//
//            case DELETE:
//                query
//                    .append("DELETE FROM ")
//                    .append(tableName);
//                break;
//
//            case INSERT:
//                if (
//                    columns.isEmpty() ||
//                    values.isEmpty() ||
//                    columns.size() != values.size()
//                ) {
//                    throw new IllegalStateException(
//                        "Columns and values must be specified and must match in count for an INSERT operation.");
//                }
//                String columnsPartForInsert = String.join(", ", columns);
//                String valuesPart = String.join(", ", values);
//                query
//                    .append("INSERT INTO ").append(tableName)
//                    .append(" (").append(columnsPartForInsert).append(") ")
//                    .append("VALUES (").append(valuesPart).append(")");
//                break;
//
//            default:
//                throw new UnsupportedOperationException("Operation not supported: " + operation);
//        }
//
//        if (!conditions.isEmpty() && operation.equals(SELECT)) {
//            query.append(" WHERE ").append(String.join(" ", conditions));
//        }
//
//        return query.toString();
    }



}
