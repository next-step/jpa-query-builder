package persistence.sql.ddl;

import jakarta.persistence.Transient;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ColumnQueries {
    private static final String DELIMITER = ", ";
    private static final String START_SYMBOL = "(";
    private static final String END_SYMBOL = ")";
    private final List<Column> queries;

    public ColumnQueries(List<Column> queries) {
        this.queries = queries;
    }

    public static ColumnQueries of(Class<?> target) {
        return new ColumnQueries(getTargetFields(target).stream()
                .map(Column::of)
                .collect(Collectors.toList()));
    }

    private static List<Field> getTargetFields(Class<?> target) {
        return Arrays.stream(target.getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .collect(Collectors.toList());
    }

    public String toQuery() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(START_SYMBOL);
        List<String> columnQueries = queries.stream()
                .map(Column::toQuery)
                .collect(Collectors.toList());
        stringBuilder.append(String.join(DELIMITER, columnQueries));
        stringBuilder.append(END_SYMBOL);
        return stringBuilder.toString();
    }
}
