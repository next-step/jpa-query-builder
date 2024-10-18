package persistence.sql.dml.query;

import persistence.sql.dml.query.metadata.SelectMetadata;
import persistence.sql.dml.query.metadata.WhereCondition;

import java.util.List;
import java.util.stream.Collectors;

public class SelectQueryBuilder implements DMLQueryBuilder {

    private static final String SELECT = "select";
    private static final String FROM = "from";
    private static final String WHERE = "where";
    private static final String AND = "and";

    @Override
    public String build(Class<?> clazz) {
        return build(clazz, List.of());
    }

    public String build(Class<?> clazz, List<WhereCondition> whereConditions) {
        SelectMetadata selectMetadata = new SelectMetadata(clazz, whereConditions);

        StringBuilder builder = new StringBuilder();
        builder.append( SELECT )
                .append(" ")
                .append( columnsClause(selectMetadata.columnNames()) )
                .append( " " )
                .append( FROM )
                .append( " " )
                .append( selectMetadata.tableName().value() )
                .append( whereClause(selectMetadata.whereConditions()) );

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
