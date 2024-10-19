package persistence.sql.dml.query.builder;

import java.util.List;
import java.util.stream.Collectors;
import persistence.sql.dialect.Dialect;
import persistence.sql.dml.query.InsertQuery;
import persistence.sql.query.QueryBuilder;

public class InsertQueryBuilder implements QueryBuilder {

    private static final String COLUMN_INSERT_STRING = "insert into table";
    private static final String COLUMN_VALUE_STRING = "values";

    @Override
    public String build(Class<?> clazz, Dialect dialect) {
        InsertQuery insertQuery = new InsertQuery(clazz);

        StringBuilder builder = new StringBuilder();
        builder.append( COLUMN_INSERT_STRING )
                .append( " " )
                .append( insertQuery.tableName() )
                .append( columnsClauseWithParentheses(insertQuery.columnNames()) )
                .append( " " )
                .append( COLUMN_VALUE_STRING )
                .append( valueClause(insertQuery) );

        return builder.toString();
    }

    public String columnsClauseWithParentheses(List<String> columnNames) {
        StringBuilder builder = new StringBuilder();
        return builder.append( " (" )
                .append( columnsClause(columnNames) )
                .append(")")
                .toString();
    }

    private String valueClause(InsertQuery query) {
        StringBuilder builder = new StringBuilder();
        return builder.append(" (")
                .append( query.columnNames().stream()
                        .map(name -> "?")
                        .collect(Collectors.joining(", ")) )
                .append(")")
                .toString();
    }

}
