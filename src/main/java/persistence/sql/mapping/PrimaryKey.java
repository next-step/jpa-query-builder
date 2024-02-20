package persistence.sql.mapping;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class PrimaryKey {

    private final ArrayList<Column> columns = new ArrayList<>();

    public String sqlConstraintString() {
        final StringBuilder statement = new StringBuilder("primary key (");

        final String columnsStatement = columns.stream().map(Column::getName).collect(Collectors.joining(", "));

        return statement.append(columnsStatement).append(')').toString();
    }

    public void addColumn(final Column column) {
        this.columns.add(column);
    }
}
