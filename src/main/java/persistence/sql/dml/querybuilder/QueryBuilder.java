package persistence.sql.dml.querybuilder;

import static persistence.sql.dml.querybuilder.QueryOperation.DELETE;
import static persistence.sql.dml.querybuilder.QueryOperation.INSERT;
import static persistence.sql.dml.querybuilder.QueryOperation.SELECT;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class QueryBuilder {

    private static final String AND_SQL = "AND ";
    private static final String OR_SQL = "OR ";

    private QueryBuilderTableName queryBuilderTableName;
    private final List<String> columns;
    private final List<String> conditions;
    private final List<String> values;
    private QueryOperation operation;

    public QueryBuilder() {
        this.columns = new ArrayList<>();
        this.conditions = new ArrayList<>();
        this.values = new ArrayList<>();
        this.operation = SELECT;
    }

    public QueryBuilder select(String columns) {
        this.columns.addAll(new QueryBuilderColumns(columns).getColumns());
        return this;
    }

    public QueryBuilder delete() {
        this.operation = DELETE;
        return this;
    }

    public QueryBuilder insertInto(String tableName) {
        this.operation = INSERT;
        this.queryBuilderTableName = new QueryBuilderTableName(tableName);
        return this;
    }

    public QueryBuilder update(String tableName) {
        this.operation = QueryOperation.UPDATE;
        this.queryBuilderTableName = new QueryBuilderTableName(tableName);
        return this;
    }

    public QueryBuilder set(String column, String value) {
        this.columns.add(column);
        this.values.add(value);
        return this;
    }

    public QueryBuilder columns(String columns) {
        this.columns.addAll(new QueryBuilderColumns(columns).getColumns());
        return this;
    }

    public QueryBuilder values(String values) {
        this.values.addAll(new QueryBuilderValues(values).getValues());
        return this;
    }

    public QueryBuilder from(String tableName) {
        this.queryBuilderTableName = new QueryBuilderTableName(tableName);
        return this;
    }

    public QueryBuilder where(String condition) {
        this.conditions.add(condition);
        return this;
    }

    public QueryBuilder and(String condition) {
        andCondition(condition);
        return this;
    }

    public QueryBuilder or(String condition) {
        orCondition(condition);
        return this;
    }

    public String build() {
        return operation.buildQuery(this);
    }

    private void andCondition(String condition) {
        if (!this.conditions.isEmpty()) {
            this.conditions.add(AND_SQL + condition);
        } else {
            where(condition);
        }
    }

    private void orCondition(String condition) {
        if (!this.conditions.isEmpty()) {
            this.conditions.add(OR_SQL + condition);
        } else {
            where(condition);
        }
    }

}
