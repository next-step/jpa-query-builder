package persistence.sql.dml;

import java.util.Arrays;
import java.util.stream.Collectors;
import persistence.sql.ddl.dialect.Dialect;
import persistence.sql.extract.ColumnPropertyExtractor;
import persistence.sql.extract.TablePropertyExtractor;

public class InsertQueryBuilder implements DMLQueryBuilder {

    private static final String COLUMN_INSERT_STRING = "insert into table";
    private static final String COLUMN_VALUE_STRING = "values";

    @Override
    public String build(Class<?> clazz, Dialect dialect) {
        InsertMetadata insertMetadata = new InsertMetadata(
                TablePropertyExtractor.getName(clazz),
                Arrays.stream(clazz.getDeclaredFields())
                        .map(ColumnPropertyExtractor::getName)
                        .toList()
        );

        StringBuilder builder = new StringBuilder();
        builder.append( COLUMN_INSERT_STRING )
                .append( " " )
                .append( insertMetadata.tableName() )
                .append( columnsClause(builder, insertMetadata) )
                .append( COLUMN_VALUE_STRING )
                .append( valueClause(builder, insertMetadata) );

        return builder.toString();
    }

    private StringBuilder columnsClause(StringBuilder builder, InsertMetadata metadata) {
        return builder.append( " (" )
                .append( String.join(",", metadata.columnNames()) )
                .append(")");
    }

    private StringBuilder valueClause(StringBuilder builder, InsertMetadata metadata) {
        return builder.append(" (")
                .append( metadata.columnNames().stream()
                        .map(name -> "?")
                        .collect(Collectors.joining(",")) )
                .append(")");
    }

}
