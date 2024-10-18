package persistence.sql.dml.query;

import persistence.sql.dml.query.metadata.DeleteMetadata;
import persistence.sql.dml.query.metadata.WhereCondition;

import java.util.List;
import java.util.stream.Collectors;

public class DeleteQueryBuilder implements DMLQueryBuilder {

    private static final String COLUMN_DELETE_STRING = "delete from";
    private static final String WHERE = "where";
    private static final String AND = "and";

    @Override
    public String build(Class<?> clazz) {
        return build(clazz, List.of());
    }

    public String build(Class<?> clazz, List<WhereCondition> whereConditions) {
        DeleteMetadata deleteMetadata = new DeleteMetadata(clazz);

        StringBuilder builder = new StringBuilder();
        builder.append( COLUMN_DELETE_STRING )
                .append( " " )
                .append( deleteMetadata.tableName().value() )
                .append( " " )
                .append( whereClause(whereConditions) );

        return builder.toString();
    }

    private String whereClause( List<WhereCondition> whereConditions ) {
        if (whereConditions.isEmpty()) {
            return "";
        }

        StringBuilder builder = new StringBuilder();
        builder.append( " " )
                .append( WHERE )
                .append( whereConditions.stream()
                        .map(columnConditionClause())
                        .collect(Collectors.joining(AND, " ", ""))
                );
        return builder.toString();
    }
}
