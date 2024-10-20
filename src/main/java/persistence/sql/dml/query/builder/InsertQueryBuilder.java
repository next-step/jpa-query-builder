package persistence.sql.dml.query.builder;

import java.util.List;
import java.util.stream.Collectors;
import persistence.sql.dialect.Dialect;
import persistence.sql.dml.query.ColumnNameValue;
import persistence.sql.metadata.ColumnName;
import persistence.sql.metadata.TableName;
import persistence.sql.query.QueryClauseGenerator;

public class InsertQueryBuilder {

    private static final String INSERT_INTO = "insert into";
    private static final String VALUES = "values";

    private final Dialect dialect;
    private final StringBuilder queryString;

    private InsertQueryBuilder(Dialect dialect) {
        this.dialect = dialect;
        this.queryString = new StringBuilder();
    }

    public static InsertQueryBuilder builder(Dialect dialect) {
        return new InsertQueryBuilder(dialect);
    }

    public String build() {
        return queryString.toString();
    }

    public InsertQueryBuilder insert(TableName tableName, List<ColumnNameValue> columns) {
        queryString.append( INSERT_INTO )
                .append( " " )
                .append( tableName.value() )
                .append( columnClause(columns.stream().map(ColumnNameValue::columnName).toList()) );
        return this;
    }

    public InsertQueryBuilder values(List<ColumnNameValue> columns) {
        queryString.append( " " )
                .append( VALUES )
                .append( valueClause(columns) );
        return this;
    }

    private String columnClause(List<ColumnName> columnNames) {
        return new StringBuilder()
                .append( " (" )
                .append( QueryClauseGenerator.columnClause(columnNames))
                .append( ")" )
                .toString();
    }

    private String valueClause(List<ColumnNameValue> columns) {
        return new StringBuilder()
                .append(" (")
                .append( columns.stream()
                        .map(ColumnNameValue::columnValueString)
                        .collect(Collectors.joining(", ")) )
                .append(")")
                .toString();
    }

}
