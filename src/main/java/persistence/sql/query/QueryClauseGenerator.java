package persistence.sql.query;

import java.util.List;
import java.util.stream.Collectors;
import persistence.sql.dml.query.WhereCondition;
import persistence.sql.metadata.ColumnName;

public class QueryClauseGenerator {

    private static final String WHERE = "where";
    private static final String AND = "and";

    public static String columnClause(List<ColumnName> columnNames) {
        return columnNames.stream()
                .map(ColumnName::value)
                .collect(Collectors.joining(", "));
    }

    public static String whereClause(List<WhereCondition> whereConditions) {
        return new StringBuilder()
                .append( " " )
                .append( WHERE )
                .append(
                        whereConditions.stream()
                        .map(condition -> condition.columnName() + " " + condition.operator() + " " + condition.value())
                        .collect(Collectors.joining(AND, " ", ""))
                ).toString();
    }

}
