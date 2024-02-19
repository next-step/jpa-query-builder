package persistence.sql.query;

import java.util.Arrays;
import java.util.List;

public class Query {

    private final Table table;

    private final List<Column> columns;

    public Query(Table table) {
        this(table, List.of());
    }

    public Query(Table table, Column... columns) {
        this(table, Arrays.stream(columns).toList());
    }

    public Query(Table table, List<Column> columns) {
        this.table = table;
        this.columns = columns;
    }

    public String getTableName() {
        return this.table.getName();
    }

    public List<Column> getColumns() {
        return this.columns
                .stream()
                .map(Column::new)
                .toList();
    }
}
