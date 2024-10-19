package persistence.sql.dml.query.builder;

import persistence.sql.dialect.Dialect;
import persistence.sql.dml.query.SelectQuery;
import persistence.sql.query.QueryBuilder;

public class SelectQueryBuilder implements QueryBuilder {

    private static final String SELECT = "select";
    private static final String FROM = "from";

    @Override
    public String build(Class<?> clazz, Dialect dialect) {
        SelectQuery selectQuery = new SelectQuery(clazz);

        StringBuilder builder = new StringBuilder();
        builder.append(SELECT)
                .append(" ")
                .append(columnsClause(selectQuery.columnNames()))
                .append(" ")
                .append(FROM)
                .append(" ")
                .append(selectQuery.tableName().value())
                .append( whereClause(clazz) );

        return builder.toString();
    }

}
