package persistence.sql.ddl;

import java.util.List;
import java.util.stream.Collectors;

public class Columns {
    private final List<Column> columns;

    public Columns(List<Column> columns) {
        this.columns = columns;
    }

    public String buildColumnsToCreate() {
        return columns.stream()
                .filter(x -> !x.isTransient())
                .map(Column::buildColumnToCreate)
                .collect(Collectors.joining(", "));
    }

    public String buildColumnsToInsert() {
        return columns.stream()
                .map(Column::getName)
                .collect(Collectors.joining(", "));
    }

}
