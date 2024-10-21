package persistence.sql.dml.querybuilder;

import java.util.Arrays;
import java.util.List;

public class QueryBuilderValues {

    private final String values;

    public QueryBuilderValues(String values) {
        validateValues(values);
        this.values = values;
    }

    public List<String> getValues() {
        return Arrays.stream(values.split(","))
            .map(String::trim)
            .toList();
    }

    private void validateValues(String values) {
        if (values == null || values.isEmpty()) {
            throw new IllegalArgumentException("Values must be specified.");
        }
    }

}
