package persistence.sql.dml.query.builder;

import java.util.List;
import java.util.stream.Collectors;
import persistence.sql.dialect.Dialect;
import persistence.sql.metadata.ColumnName;
import persistence.sql.metadata.TableName;
import persistence.sql.query.QueryClauseGenerator;

public class InsertQueryBuilder {

    private static final String INSERT_INTO_TABLE = "insert into table";
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

    public InsertQueryBuilder insert(TableName tableName, List<ColumnName> columnNames) {
        queryString.append( INSERT_INTO_TABLE )
                .append( " " )
                .append( tableName.value() )
                .append( columnClause(columnNames) );
        return this;
    }

    public InsertQueryBuilder values(List<ColumnName> columnNames) {
        queryString.append( " " )
                .append( VALUES )
                .append( valueClause(columnNames) );
        return this;
    }

    private String columnClause(List<ColumnName> columnNames) {
        return new StringBuilder()
                .append( " (" )
                .append( QueryClauseGenerator.columnClause(columnNames) )
                .append( ")" )
                .toString();
    }

    private String valueClause(List<ColumnName> columnNames) {
        return new StringBuilder()
                .append(" (")
                .append( columnNames.stream()
                        .map(name -> "?")
                        .collect(Collectors.joining(", ")) )
                .append(")")
                .toString();
    }

}
