package persistence.sql.metadata;

import persistence.dialect.Dialect;

import java.util.List;
import java.util.stream.Collectors;

public class Columns {
    private final List<Column> columns;

    public Columns(List<Column> columns) {
        this.columns = columns;
    }

    public String buildColumnsToCreate(Dialect dialect) {
        return columns.stream()
                .filter(x -> !x.isTransient())
                .map(x -> x.buildColumnToCreate(dialect))
                .collect(Collectors.joining(", "));
    }

    public String buildColumnsToInsert() {
        return columns.stream()
                .map(Column::getName)
                .collect(Collectors.joining(", "));
    }

}
