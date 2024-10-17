package persistence.sql.dml;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class InsertQueryBuilder implements DMLQueryBuilder {

    private static final String COLUMN_INSERT_STRING = "insert into table";
    private static final String COLUMN_VALUE_STRING = "values";

    @Override
    public String build(Class<?> clazz) {
        InsertMetadata insertMetadata = new InsertMetadata(
                new TableName(clazz),
                Arrays.stream(clazz.getDeclaredFields())
                        .map(ColumnName::new)
                        .toList()
        );

        StringBuilder builder = new StringBuilder();
        builder.append( COLUMN_INSERT_STRING )
                .append( " " )
                .append( insertMetadata.tableName().value() )
                .append( columnsClauseWithParentheses(insertMetadata.columnNames()) )
                .append( " " )
                .append( COLUMN_VALUE_STRING )
                .append( valueClause(insertMetadata) );

        return builder.toString();
    }

    public String columnsClauseWithParentheses(List<ColumnName> columnNames) {
        StringBuilder builder = new StringBuilder();
        return builder.append( " (" )
                .append( columnsClause(columnNames) )
                .append(")")
                .toString();
    }

    private String valueClause(InsertMetadata metadata) {
        StringBuilder builder = new StringBuilder();
        return builder.append(" (")
                .append( metadata.columnNames().stream()
                        .map(name -> "?")
                        .collect(Collectors.joining(", ")) )
                .append(")")
                .toString();
    }

}
