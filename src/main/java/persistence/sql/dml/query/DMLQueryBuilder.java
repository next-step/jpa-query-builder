package persistence.sql.dml.query;

import persistence.sql.dml.query.metadata.ColumnName;
import persistence.sql.dml.query.metadata.WhereCondition;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface DMLQueryBuilder {

    String build(Class<?> clazz);

    default String columnsClause(List<ColumnName> columnNames) {
        StringBuilder builder = new StringBuilder();
        return builder.append(columnNames.stream()
                        .map(ColumnName::value)
                        .collect(Collectors.joining(", ")))
                .toString();
    }

    default Function<WhereCondition, String> columnConditionClause() {
        return condition -> condition.columnName().value()
                + " " + condition.operator() + " ?";
    }

}
