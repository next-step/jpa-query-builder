package persistence.sql.metadata;

import java.util.List;

public class Columns {

    private final List<Column> columns;

    private Columns(List<Column> columns) {
        this.columns = columns;
    }

    public static Columns of(List<Column> columns) {
        return new Columns(columns);
    }

    public List<Column> getColumns() {
        return columns;
    }
}
