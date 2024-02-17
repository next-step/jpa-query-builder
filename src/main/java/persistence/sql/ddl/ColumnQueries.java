package persistence.sql.ddl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ColumnQueries {
    private static final String DELIMITER = ", ";
    private static final String START_SYMBOL = "(";
    private static final String END_SYMBOL = ")";
    private final List<ColumnQuery> queries;

    public ColumnQueries(List<ColumnQuery> queries) {
        this.queries = queries;
    }

    public static ColumnQueries of(Class<?> target) {
        return new ColumnQueries(Arrays.stream(target.getDeclaredFields())
                .map(ColumnQuery::of)
                .collect(Collectors.toList()));
    }

    public String toQuery() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(START_SYMBOL);
        List<String> columnQueries = queries.stream()
                .map(ColumnQuery::toQuery)
                .collect(Collectors.toList());
        stringBuilder.append(String.join(DELIMITER, columnQueries));
        stringBuilder.append(END_SYMBOL);
        return stringBuilder.toString();
    }
}
