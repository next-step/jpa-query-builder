package persistence.sql.dml;

import java.util.List;
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

}
