package persistence.sql.dml.querybuilder;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SetClause {

    private final QueryBuilder builder;

    public SetClause(QueryBuilder builder) {
        this.builder = builder;
    }

    public String getClause() {
        List<String> columns = builder.getColumns();
        List<String> values = builder.getValues();

        if (columns.isEmpty() || values.isEmpty() || columns.size() != values.size()) {
            throw new IllegalStateException("Columns and values must be specified and must match in count.");
        }

        return IntStream.range(0, columns.size())
            .mapToObj(i -> columns.get(i) + " = " + values.get(i))
            .collect(Collectors.joining(", "));
    }

}
