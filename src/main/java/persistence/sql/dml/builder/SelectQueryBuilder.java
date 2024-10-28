package persistence.sql.dml.builder;


import persistence.dialect.Dialect;
import persistence.metadata.ColumnName;
import persistence.metadata.TableName;
import persistence.metadata.WhereCondition;

import java.util.List;

import static persistence.clause.QueryClause.columnClause;
import static persistence.clause.QueryClause.whereClause;

public class SelectQueryBuilder {
    private static final String SELECT = "select";
    private static final String FROM = "from";

    private final StringBuilder queryString;

    public SelectQueryBuilder(Dialect dialect) {
        this.queryString = new StringBuilder();
    }

    public static SelectQueryBuilder builder(Dialect dialect) {
        return new SelectQueryBuilder(dialect);
    }

    public String build() {
        return queryString.toString();
    }

    public SelectQueryBuilder select(List<ColumnName> columnNames) {
        this.queryString.append(SELECT).append(" ").append(columnClause(columnNames));
        return this;
    }

    public SelectQueryBuilder from(TableName table) {
        this.queryString.append(" ").append(FROM).append(" ").append(table.name());
        return this;
    }

    public SelectQueryBuilder where(List<WhereCondition> whereConditions) {
        if (whereConditions.isEmpty()) {
            return this;
        }

        this.queryString.append(whereClause(whereConditions));
        return this;
    }
}
