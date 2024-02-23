package persistence.sql.ddl.field;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class QueryFields {
    public static final String DELIMITER = "," + System.lineSeparator();
    private final List<QueryField> values;

    public QueryFields(Field[] fields) {
        this.values = Arrays.stream(fields)
                .map(QueryField::new)
                .collect(Collectors.toUnmodifiableList());
    }

    public String toSQL() {
        return values.stream()
                .map(QueryField::toSQL)
                .distinct()
                .collect(Collectors.joining(DELIMITER));
    }
}
