package persistence.sql.ddl.domain;

import java.util.List;
import java.util.stream.Collectors;

public class Columns {

    private static final String COMMA = ", ";

    private final List<Column> columns;

    public Columns(List<Column> columns) {
        this.columns = columns;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public String getDDLColumns() {
        return columns.stream()
                .filter(Column::isNotTransient)
                .map(Column::getDDLColumn)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.joining(COMMA));
    }

    public String getInsertColumns() {
        return columns.stream()
                .filter(Column::isNotTransient)
                .filter(Column::isNotIdAutoGenerated)
                .map(Column::getDMLColumn)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.joining(COMMA));
    }

    public String getSelectColumns() {
        return columns.stream()
                .filter(Column::isNotTransient)
                .map(Column::getDMLColumn)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.joining(COMMA));
    }
}
