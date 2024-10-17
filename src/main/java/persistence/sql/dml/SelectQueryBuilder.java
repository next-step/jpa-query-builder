package persistence.sql.dml;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;

public class SelectQueryBuilder implements DMLQueryBuilder {

    private static final String SELECT = "select";
    private static final String FROM = "from";
    private static final String WHERE = "where";
    private static final String AND = "and";

    @Override
    public String build(Class<?> clazz) {
        SelectMetadata selectMetadata = new SelectMetadata(
                new TableName(clazz),
                Arrays.stream(clazz.getDeclaredFields())
                        .map(ColumnName::new)
                        .toList()
        );

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
                .append( " " )
                .append( whereConditions.stream()
                        .map(columnConditionClause())
                        .collect(Collectors.joining(AND, " ", " "))
                );
        return builder.toString();
    }

    @NotNull
    private Function<WhereCondition, String> columnConditionClause() {
        return condition -> condition.columnName().value() + " " + condition.operator() + "?";
    }

}
