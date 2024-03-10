package persistence.sql.metadata;

import java.util.List;

public class ColumnsMetadata {

    private final List<ColumnMetadata> columns;

    private ColumnsMetadata(List<ColumnMetadata> columns) {
        this.columns = columns;
    }

    public static ColumnsMetadata of(List<ColumnMetadata> columns) {
        return new ColumnsMetadata(columns);
    }

    public List<ColumnMetadata> getColumns() {
        return columns;
    }

    public ColumnMetadata getColumn(String name) {
        return columns.stream()
                .filter(column -> column.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Column not found"));
    }
}
