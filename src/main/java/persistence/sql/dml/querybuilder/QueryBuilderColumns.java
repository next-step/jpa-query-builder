package persistence.sql.dml.querybuilder;

import java.util.Arrays;
import java.util.List;

public class QueryBuilderColumns {

    private final String columns;

    public QueryBuilderColumns(String columns) {
        validateSelectColumns(columns);
        this.columns = columns;
    }

    public List<String> getColumns() {
        return Arrays.stream(columns.split(","))
            .map(String::trim)
            .toList();
    }

    private void validateSelectColumns(String columns) {
        if (columns == null || columns.isEmpty()) {
            throw new IllegalArgumentException("Columns must be specified.");
        }
    }

}
