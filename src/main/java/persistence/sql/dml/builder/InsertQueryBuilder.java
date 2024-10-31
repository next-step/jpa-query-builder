package persistence.sql.dml.builder;

import persistence.clause.QueryClause;
import persistence.dialect.Dialect;
import persistence.metadata.ColumnName;
import persistence.metadata.FieldValue;
import persistence.metadata.TableName;

import java.util.List;
import java.util.stream.Collectors;

public class InsertQueryBuilder {
    private static final String INSERT_INTO = "insert into";
    private static final String VALUES = "values";

    private final Dialect dialect;
    private final StringBuilder queryString;

    public InsertQueryBuilder(Dialect dialect) {
        this.dialect = dialect;
        this.queryString = new StringBuilder();
    }

    public static InsertQueryBuilder builder(Dialect dialect) {
        return new InsertQueryBuilder(dialect);
    }

    public String build() {
        return queryString.toString();
    }

    public InsertQueryBuilder insert(TableName table, List<FieldValue> columns) {
        queryString.append( INSERT_INTO )
                .append( " " )
                .append( table.name() )
                .append( columnClause(columns.stream().map(FieldValue::columnName).toList()) );
        return this;
    }

    public InsertQueryBuilder values(List<FieldValue> columns) {
        queryString.append( " " )
                .append( VALUES )
                .append( valueClause(columns) );
        return this;
    }

    private String columnClause(List<ColumnName> columnNames) {
        return " (" +
                QueryClause.columnClause(columnNames) +
                ")";
    }

    private String valueClause(List<FieldValue> columns) {
        return " (" +
                columns.stream()
                        .map(FieldValue::fieldValueHandleToString)
                        .collect(Collectors.joining(", ")) +
                ")";
    }
}
