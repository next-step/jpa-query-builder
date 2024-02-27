package persistence.sql.ddl.domain;

import java.util.List;

public class Columns {

    private final List<Column> columns;

    public Columns(List<Column> columns) {
        this.columns = columns;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public Column getPrimaryKey() {
        return columns.stream()
                .filter(Column::isPrimaryKey)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Primary key not found."));
    }

}
